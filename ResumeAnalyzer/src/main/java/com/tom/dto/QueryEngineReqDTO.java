package com.tom.dto;

public class QueryEngineReqDTO {
	private String skills;

    private String yearsOfExpUpperBound;

    private String yearsOfExpLowerBound;

    public String getSkills ()
    {
        return skills;
    }

    public void setSkills (String skills)
    {
        this.skills = skills;
    }

    public String getYearsOfExpUpperBound ()
    {
        return yearsOfExpUpperBound;
    }

    public void setYearsOfExpUpperBound (String yearsOfExpUpperBound)
    {
        this.yearsOfExpUpperBound = yearsOfExpUpperBound;
    }

    public String getYearsOfExpLowerBound ()
    {
        return yearsOfExpLowerBound;
    }

    public void setYearsOfExpLowerBound (String yearsOfExpLowerBound)
    {
        this.yearsOfExpLowerBound = yearsOfExpLowerBound;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [skills = "+skills+", yearsOfExpUpperBound = "+yearsOfExpUpperBound+", yearsOfExpLowerBound = "+yearsOfExpLowerBound+"]";
    }
}
