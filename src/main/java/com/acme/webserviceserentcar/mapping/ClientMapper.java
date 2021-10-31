package com.acme.webserviceserentcar.mapping;

import com.acme.webserviceserentcar.domain.model.entity.Client;
import com.acme.webserviceserentcar.resource.ClientResource;
import com.acme.webserviceserentcar.resource.CreateClientResource;
import com.acme.webserviceserentcar.resource.UpdateClientResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ClientMapper {
    @Autowired
    EnhancedModelMapper mapper;

    //Object Mapping

    public ClientResource toResource(Client model) { return mapper.map(model, ClientResource.class); }

    public Page<ClientResource> modelListToPage(List<ClientResource> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, ClientResource.class), pageable, modelList.size());
    }

    public Client ToModel(CreateClientResource resource) { return mapper.map(resource, Client.class); }
    public Client ToModel(UpdateClientResource resource) { return mapper.map(resource, Client.class); }
}
