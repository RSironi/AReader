package com.tcc.areader.requests;

import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Singleton;
import com.cloudinary.StoredFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AnnotationRequest extends StoredFile {
  public String text;
  public MultipartFile file;
  @JsonIgnore
  public String imgUrl;

  public String getUrl() {
    if (version != null && format != null && publicId != null) {
      return Singleton.getCloudinary().url()
          .resourceType(resourceType)
          .type(type)
          .format(format)
          .version(version)
          .generate(publicId);
    } else
      return null;
  }

  public String getComputedSignature() {
    return getComputedSignature(Singleton.getCloudinary());
  }

  public boolean validSignature() {
    return getComputedSignature().equals(signature);
  }
}