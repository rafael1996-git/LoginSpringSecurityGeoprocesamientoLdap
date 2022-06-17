package com.ine.cartografia.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

//Lombok annotations
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CaptchaResponse {

    boolean success;
    public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public LocalDateTime getChallengeTs() {
		return challengeTs;
	}
	public void setChallengeTs(LocalDateTime challengeTs) {
		this.challengeTs = challengeTs;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public List<String> getErrorCodes() {
		return errorCodes;
	}
	public void setErrorCodes(List<String> errorCodes) {
		this.errorCodes = errorCodes;
	}
	@JsonProperty("challenge_ts")
    LocalDateTime challengeTs;
    String hostname;
    @JsonProperty("error-codes")
    List<String> errorCodes;
}
