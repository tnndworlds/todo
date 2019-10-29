package com.mailang.orgimpl;


import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class XSObjectMapper extends ObjectMapper
{
    public XSObjectMapper()
    {
        this.setSerializationInclusion(Include.NON_NULL);
        this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

}
