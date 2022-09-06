package com.acme.webserviceserentcar.client.mapping;

import com.acme.webserviceserentcar.client.domain.model.entity.Plan;
import com.acme.webserviceserentcar.client.resource.create.CreatePlanResource;
import com.acme.webserviceserentcar.client.resource.PlanResource;
import com.acme.webserviceserentcar.client.resource.update.UpdatePlanResource;
import com.acme.webserviceserentcar.shared.mapping.EnhancedModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class PlanMapper implements Serializable {
    @Autowired
    private EnhancedModelMapper mapper;

    //Object Mapping

    public PlanResource toResource(Plan model) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return mapper.map(model, PlanResource.class);
    }

    public Page<PlanResource> modelListToPage(List<Plan> modelList, Pageable pageable) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return new PageImpl<>(mapper.mapList(modelList, PlanResource.class), pageable, modelList.size());
    }

    public Plan toModel(CreatePlanResource resource) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(resource, Plan.class);
    }

    public Plan toModel(UpdatePlanResource resource) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(resource, Plan.class);
    }
}
