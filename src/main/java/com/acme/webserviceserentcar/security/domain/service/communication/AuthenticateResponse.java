package com.acme.webserviceserentcar.security.domain.service.communication;

import com.acme.webserviceserentcar.security.resource.AuthenticateResource;
import com.acme.webserviceserentcar.shared.domain.service.communication.BaseResponse;

public class AuthenticateResponse extends BaseResponse<AuthenticateResource> {
    public AuthenticateResponse(String message) {
        super(message);
    }

    public AuthenticateResponse(AuthenticateResource resource) {
        super(resource);
    }
}
