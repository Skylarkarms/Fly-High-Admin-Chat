package com.example.flyhighadminchat.fireBase_user_package;

public class UserFactory {

    private UserFactory() {
    }

    public static UserFactory getInstance() {
        return new UserFactory();
    }

    public UserNotif getNewUserNotif(long request, long open) {
        return new UserNotif(request, open);
    }

//    public UserNotif getNewUserNotif(boolean request, boolean open) {
//        UserNotif userNotif;
//        return userNotif = new UserNotif(request, open);
//    }
//
    public FlyHighUser getNewFlyHighUser(String _uId, String _username) {
        return new FlyHighUser(_uId, _username);
    }

    public User getNewUser(String _email, String _name, int _phone) {
        User user;
        return user = new User(_email,_name,_phone);
    }

}
