// IUserInfoController.aidl
package com.example.myservice;

// Declare any non-default types here with import statements
import com.example.myservice.UserInfo;

interface IUserInfoController {
   void changeUserName(out UserInfo userInfo);
}
