public class Specialist extends User {

    private String specialistType;

    public Specialist(){
    }

    Specialist(int id, String firstname, String lastname, char[] password, String specialistType){
        super(id, firstname, lastname, password);
        this.specialistType = specialistType;
    }

    public String getSpecialistType() {
        return specialistType;
    }

    public void setSpecialistType(String specialistType) {
        this.specialistType = specialistType;
    }
}
