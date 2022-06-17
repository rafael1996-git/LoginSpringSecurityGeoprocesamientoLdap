package com.ine.cartografia.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

//Lombok annotations
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HelloDto {

    @NonNull
    String name="hola soy rafael";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
