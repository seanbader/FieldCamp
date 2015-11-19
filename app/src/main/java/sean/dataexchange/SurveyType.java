package sean.dataexchange;

/**
 * Created by g6 on 11/19/15.
 */
public enum SurveyType {
    FLAGGING("Flagging"),
    DGPS("Differential G.P.S."),
    GRAVITY("Gravity"),
    SEISMIC("Seismic"),
    RESISTIVITY("D.C. Resistivity"),
    TEM("Electromagnetics"),
    MT("Magnetotellurics"),
    HAMMER("Hammer Seismic"),
    SP("Self Potential"),
    MAGNETICS("Magnetics");

    private String description;

    SurveyType(String desc) {
        this.description = desc;
    }

    public static SurveyType fromString(String text) {
        if (text != null) {
            for (SurveyType b : SurveyType.values()) {
                if (text.equalsIgnoreCase(b.description)) {
                    return b;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
