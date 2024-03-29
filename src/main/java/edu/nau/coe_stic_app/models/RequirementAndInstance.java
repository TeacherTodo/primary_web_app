package edu.nau.coe_stic_app.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RequirementAndInstance {

    Requirement requirement;
    RequirementInstance requirementInstance;

    public RequirementAndInstance() {
    }

    public RequirementAndInstance(Requirement requirement, RequirementInstance requirementInstance) {
        this.requirement = requirement;
        this.requirementInstance = requirementInstance;
    }

    public static RequirementAndInstance create(RequirementInstance requirementInstance, List<Requirement> requirements) {
        if (requirementInstance == null) {
            return null;
        }

        if (requirements == null) {
            return null;
        }

        if (requirements.size() == 0) {
            return null;
        }

        Requirement req = requirements.stream().filter((requirement) -> {
            return requirement.getID() == requirementInstance.getRequirementID();
        }).findFirst().orElse(null);

        // Unable to find a requirement to match with requirementInstance
        if (req == null) {
            return null;
        }

        return new RequirementAndInstance(req, requirementInstance);
    }

    public static List<RequirementAndInstance> create(List<RequirementInstance> requirementInstances, List<Requirement> requirements) {
        if (requirementInstances == null) {
            return null;
        }

        if (requirements == null) {
            return null;
        }

        if (requirementInstances.size() == 0) {
            return null;
        }

        if (requirements.size() == 0) {
            return null;
        }

        // create a map of requirements
        Map<Integer, Requirement> reqIdToRequirement = requirements.stream().collect(Collectors.toMap(Requirement::getID, Function.identity()));
        List<RequirementAndInstance> requirementAndInstanceList = new ArrayList<>();

        for (RequirementInstance reqInstance : requirementInstances) {
            RequirementAndInstance reqAndInstance;
            Requirement req = reqIdToRequirement.get(reqInstance.getRequirementID());

            System.out.println("RequirementAndInstance.create() reqInstance: " + reqInstance);
//            System.out.println("RequirementAndInstance.create() req: " + req);
            System.out.println("RequirementAndInstance.create() req: " + req);

            reqAndInstance = new RequirementAndInstance(req, reqInstance);

            requirementAndInstanceList.add(reqAndInstance);
        }

        return requirementAndInstanceList;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public void setRequirement(Requirement requirement) {
        this.requirement = requirement;
    }

    public RequirementInstance getRequirementInstance() {
        return requirementInstance;
    }

    public void setRequirementInstance(RequirementInstance requirementInstance) {
        this.requirementInstance = requirementInstance;
    }

    @Override
    public String toString() {
        return "RequirementAndInstance{" +
                "requirement=" + requirement +
                ", requirementInstance=" + requirementInstance +
                '}';
    }
}
