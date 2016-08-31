package fanzone.datacollection.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START post_class]
@IgnoreExtraProperties
public class Posts {

    public String uid;
    public String author;
    //public String title;
    public String body;
    public String msurveyor,mlocation,mmatch,mname,mgender,mage,mfromloc,mphone,memail,mfevteam,mprofession,meducation,msys_time;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public Posts() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Posts( String msurveyor, String mlocation,String mmatch,String mname,String mgender,
               String mage, String mfromloc,String mphone,String memail,String mfevteam,String mprofession,String meducation,String msys_time) {
        //this.uid = uid;
        //this.author = author;;
        this.msurveyor = msurveyor;
        this.mlocation = mlocation;
        this.mmatch = mmatch;
        this.mname = mname;
        this.mgender = mgender;
        this.mage = mage;
        this.mfromloc = mfromloc;
        this.mphone = mphone;
        this.memail = memail;
        this.mfevteam = mfevteam;
        this.mprofession = mprofession;
        this.meducation = meducation;
        this.msys_time = msys_time;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
       // result.put("uid", uid);
       // result.put("author", author);
        result.put("surveyor", msurveyor);
        result.put("location", mlocation);
        result.put("match", mmatch);

        result.put("name", mname);
        result.put("gender", mgender);
        result.put("age", mage);
        result.put("fromloc", mfromloc);
        result.put("phone", mphone);
        result.put("email", memail);
        result.put("fevteam", mfevteam);
        result.put("profession", mprofession);
        result.put("meducation", meducation);
        result.put("sys_time", msys_time);


        result.put("starCount", starCount);
        result.put("stars", stars);

        return result;
    }
    // [END post_to_map]

}
// [END post_class]
