package com.driver;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessagingAppService {

    MessagingAppRepository messagingAppRepository = new MessagingAppRepository();
    public boolean userExists (String mobileNumber){
        return messagingAppRepository.userExists(mobileNumber);
    }

    public String createUser(String name,String mobile){
        return messagingAppRepository.createUser(name,mobile);
    }
    public Group createGroup(List<User> users){
        return messagingAppRepository.createGroup(users);
    }
//    public int removeUser(User user) throws Exception {
//        return whatsappRepository.removeUser(user);
//    }

    public int createMessage(String content){
        // The 'i^th' created message has message id 'i'.
        // Return the message id.

        return messagingAppRepository.createMessage(content);
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception{
        //Throw "Group does not exist" if the mentioned group does not exist
        //Throw "You are not allowed to send message" if the sender is not a member of the group
        //If the message is sent successfully, return the final number of messages in that group.

        return messagingAppRepository.sendMessage(message, sender, group);
    }

    public String changeAdmin(User approver, User user, Group group) throws Exception{
        //Throw "Group does not exist" if the mentioned group does not exist
        //Throw "Approver does not have rights" if the approver is not the current admin of the group
        //Throw "User is not a participant" if the user is not a part of the group
        //Change the admin of the group to "user" and return "SUCCESS". Note that at one time there is only one admin and the admin rights are transferred from approver to user.

        return messagingAppRepository.changeAdmin(approver, user, group);
    }

}
