package com.rage.clamber.Networking.Requests;

/**
 * Created by rage on 4/10/16.
 */
public class NewUserDataRequest {

        protected String username;
        protected int height;
        protected int skill;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getSkill() {
            return skill;
        }

        public void setSkill(int skill) {
            this.skill = skill;
        }

}
