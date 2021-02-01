/* Assignment: 1
Campus: Ashdod
Author: Lidor Zaguri, ID: 205622814
Author: Liat Golber, ID: 313301129
*/

package com.example.myapplication;

public class User {
        public String name, password,email;

        public User(){}

        public User(String name,String password,String email) {
            this.password = password;
            this.name = name;
            this.email = email;
        }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
