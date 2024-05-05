package com.example.aspireloanapi.model;

public class JwtResponse {

    private String jwtToken;

    private String userName;

    public JwtResponse() {
    }

    public JwtResponse(String jwtToken, String userName) {
        this.jwtToken = jwtToken;
        this.userName = userName;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public interface JwtTokenStep {
        UserNameStep withJwtToken(String jwtToken);
    }

    public interface UserNameStep {
        BuildStep withUserName(String userName);
    }

    public interface BuildStep {
        JwtResponse build();
    }


    public static class Builder implements JwtTokenStep, UserNameStep, BuildStep {
        private String jwtToken;
        private String userName;

        private Builder() {
        }

        public static JwtTokenStep jwtResponse() {
            return new Builder();
        }

        @Override
        public UserNameStep withJwtToken(String jwtToken) {
            this.jwtToken = jwtToken;
            return this;
        }

        @Override
        public BuildStep withUserName(String userName) {
            this.userName = userName;
            return this;
        }

        @Override
        public JwtResponse build() {
            return new JwtResponse(
                    this.jwtToken,
                    this.userName
            );
        }
    }
}
