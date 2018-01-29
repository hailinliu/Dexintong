package com.runtai.newdexintong.comment.utils.checkpermission.annotation;

import com.runtai.newdexintong.comment.utils.checkpermission.CheckAnnotatePermission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionDenied {
  int requestCode() default CheckAnnotatePermission.DEFAULT_REQUEST_CODE;
}
