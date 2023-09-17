package com.tcc.areader.utils;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.tcc.areader.requests.AnnotationRequest;

public class FileValidator implements Validator {
  public boolean supports(Class clazz) {
    return AnnotationRequest.class.equals(clazz);
  }

  public void validate(Object obj, Errors e) {
    ValidationUtils.rejectIfEmpty(e, "name", "name.empty");
    AnnotationRequest p = (AnnotationRequest) obj;
    if (p.getFile() == null || p.getFile().isEmpty()) {
      if (!p.validSignature()) {
        e.rejectValue("signature", "signature.mismatch");
      }
    }
  }
}