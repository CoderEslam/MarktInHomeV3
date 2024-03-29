package com.doubleclick.marktinhome.Repository;

import static com.doubleclick.marktinhome.BaseApplication.isNetworkConnected;
import static com.doubleclick.marktinhome.Model.Constantes.GROUPS;
import static com.doubleclick.marktinhome.Model.Constantes.USER;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.doubleclick.GroupInterface;
import com.doubleclick.marktinhome.Model.Group;
import com.doubleclick.marktinhome.Model.GroupData;
import com.doubleclick.marktinhome.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 4/22/2022
 */
public class GroupRepository extends BaseRepository {

    private GroupInterface groupInterface;
    private ArrayList<Group> allGroups = new ArrayList<>();
    private GroupData groupData = new GroupData();

    public GroupRepository(GroupInterface groupInterface) {
        this.groupInterface = groupInterface;
    }


    public void AllGroup() {
        reference.child(GROUPS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    if (isNetworkConnected()) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Group group = dataSnapshot.getValue(Group.class);
                            allGroups.add(group);
                        }
                        groupInterface.allGroups(allGroups);
                    } else {
                        ShowToast("No Internet Connection");
                    }
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getGroupDataById(String id) {
        reference.child(GROUPS).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    if (isNetworkConnected()) {
                        if (snapshot.exists()) {
                            Group group = snapshot.getValue(Group.class);
                            assert group != null;
                            groupData.setGroup(group);
                            reference.child(USER).child(group.getCreatedBy()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    User user = task.getResult().getValue(User.class);
//                                    ShowToast(user.getName() + "    " + user.getId());
                                    groupData.setUser(user);
                                    Log.e("groupData", groupData.toString());
                                    groupInterface.groupData(groupData);
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    Log.e("ExceptionGroupRep", e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
