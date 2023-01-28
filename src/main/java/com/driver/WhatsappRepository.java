package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashMap<String,User> userData;
    private int customGroupCount;
    private int messageId;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userData = new HashMap<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }


    public boolean userExists(String mobileNumber){
        if(userData.containsKey(mobileNumber)){
            return false;
        }
        return true;

    }
    public String createUser(String name,String mobile){
        User user = new User(name,mobile);
        userData.put(mobile,user);
        return "SUCCESS";
    }

    public Group createGroup(List<User> users){
        if(users.size()<=2){
            Group personalGroup = new Group(users.get(1).getName(),users.size());
            groupUserMap.put(personalGroup,users);
            return personalGroup;

        }
        customGroupCount++;
        Group group = new Group("Group "+customGroupCount,users.size() );
        groupUserMap.put(group,users);
        adminMap.put(group,users.get(0));
        return group;
    }

    public int createMessage(String content){
        // The 'i^th' created message has message id 'i'.
        // Return the message id.
        messageId++;
       Message message =  new Message(messageId,content,new Date());
       return messageId;
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception{
        //Throw "Group does not exist" if the mentioned group does not exist
        //Throw "You are not allowed to send message" if the sender is not a member of the group
        //If the message is sent successfully, return the final number of messages in that group.
        if(!groupUserMap.containsKey(group)) throw new Exception("Group does not exist");
        boolean check=false;
        for(User user : groupUserMap.get(group)){
            if(sender.equals(user))
            {
                check=true;
                break;
            }
        }
        if(check==false) throw new Exception("You are not allowed to send message");
        List<Message> list=new ArrayList<>();
        if(groupMessageMap.containsKey(group)) list=groupMessageMap.get(group);
        list.add(message);
        groupMessageMap.put(group,list);
        return list.size();

    }


    public String changeAdmin(User approver, User user, Group group) throws Exception{
        //Throw "Group does not exist" if the mentioned group does not exist
        //Throw "Approver does not have rights" if the approver is not the current admin of the group
        //Throw "User is not a participant" if the user is not a part of the group
        //Change the admin of the group to "user" and return "SUCCESS". Note that at one time there is only one admin and the admin rights are transferred from approver to user.
        if(!groupUserMap.containsKey(group)) throw new Exception("Group does not exist");
        if(!adminMap.get(group).equals(approver)) throw new Exception("Approver does not have rights");
        boolean check  = false;
        for(User sender : groupUserMap.get(group)){
            if(sender.equals(user)){
                check =true;
                break;
            }
        }
        if(check ==false ) throw new Exception("User is not a participant");
        adminMap.put(group,user);
        return "SUCCESS";
    }
}
