package com.example.aspireloanapi.model;

public class JwtRequest {

    private String userName;

    private String password;

    public JwtRequest() {
    }

    public JwtRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public interface UserNameStep {
        PasswordStep withUserName(String userName);
    }

    public interface PasswordStep {
        BuildStep withPassword(String password);
    }

    public interface BuildStep {
        JwtRequest build();
    }


    public static class Builder implements UserNameStep, PasswordStep, BuildStep {
        private String userName;
        private String password;

        private Builder() {
        }

        public static UserNameStep jwtRequest() {
            return new Builder();
        }

        @Override
        public PasswordStep withUserName(String userName) {
            this.userName = userName;
            return this;
        }

        @Override
        public BuildStep withPassword(String password) {
            this.password = password;
            return this;
        }

        @Override
        public JwtRequest build() {
            return new JwtRequest(
                    this.userName,
                    this.password
            );
        }
    }
}
