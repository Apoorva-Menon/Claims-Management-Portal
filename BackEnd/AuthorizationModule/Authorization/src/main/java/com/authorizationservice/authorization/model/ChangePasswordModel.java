package com.authorizationservice.authorization.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@ApiModel(description = "Creating model class for generating authorization token when any customer logs in")
public class ChangePasswordModel {
	private String username;
	private String oldPassword;
	private String newPassword;
}
