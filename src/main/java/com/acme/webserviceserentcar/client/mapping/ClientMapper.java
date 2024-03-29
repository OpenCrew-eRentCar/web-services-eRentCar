package com.acme.webserviceserentcar.client.mapping;

import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.shared.mapping.EnhancedModelMapper;
import com.acme.webserviceserentcar.client.resource.ClientResource;
import com.acme.webserviceserentcar.client.resource.create.CreateClientResource;
import com.acme.webserviceserentcar.client.resource.update.UpdateClientResource;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class ClientMapper implements Serializable {
    @Autowired
    private EnhancedModelMapper mapper;

    //Object Mapping

    public ClientResource toResource(Client model) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return mapper.map(model, ClientResource.class);
    }

    public Page<ClientResource> modelListToPage(List<Client> modelList, Pageable pageable) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return new PageImpl<>(mapper.mapList(modelList, ClientResource.class), pageable, modelList.size());
    }

    public Client toModel(CreateClientResource resource) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(resource, Client.class);
    }
    public Client toModel(UpdateClientResource resource) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(resource, Client.class);
    }
}
