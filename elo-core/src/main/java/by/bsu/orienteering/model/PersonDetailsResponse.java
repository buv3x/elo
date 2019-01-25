package by.bsu.orienteering.model;

import java.util.List;

/**
 * Created by alexey.memelov on 14-Jan-19.
 */
public class PersonDetailsResponse {

    private List<PersonReportDTO> personReport;

    private String displayName;

    public List<PersonReportDTO> getPersonReport() {
        return personReport;
    }

    public void setPersonReport(List<PersonReportDTO> personReport) {
        this.personReport = personReport;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
