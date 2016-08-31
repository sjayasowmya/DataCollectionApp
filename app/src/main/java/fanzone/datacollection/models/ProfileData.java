package fanzone.datacollection.models;

/**
 * Created by satya on 29/8/16.
 */
public class ProfileData {

    //name and address string

    public  String surveyor,location,match,name,gender,age,fromloc,phone,email,favteam,profession,education,sys_time;
    public String username;

    public ProfileData() {
      /*Blank default constructor essential for Firebase*/
    }

   // public ProfileData(String username) {
     //   this.username = username;
  //  }

   /* public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }*/
    //Getters and setters
    public String getSurveyor() {
        return surveyor;
    }

    public void setSurveyor(String surveyor) {
        this.surveyor = surveyor;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
    public String getFromloc() {
        return fromloc;
    }

    public void setFromloc(String fromloc) {
        this.fromloc = fromloc;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFavteam() {
        return favteam;
    }

    public void setFavteam(String favteam) {
        this.favteam = favteam;
    }
    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }
    public String getSys_time() {
        return sys_time;
    }

    public void setSys_time(String sys_time) {
        this.sys_time = sys_time;
    }
}
