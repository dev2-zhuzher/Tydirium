package com.vanke.tydirium.entity.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * 
 * @Description: 根据乐邦接口查询的用户信息： json 转bean 使用
 *
 * @author: vanke-yuzn05
 * @date: 2017年8月22日 上午11:47:25
 */
public class LeBangUser {
	@JsonProperty("code")
	private String code;
	@JsonProperty("result")
	private Result result;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public class Result {
		@JsonProperty("job_can_edit")
		private String jobCanEdit; // true
		@JsonProperty("updated")
		private String updated; // 更新时间，例："2017-01-23 09:56:29",
		@JsonProperty("role_identity")
		private String roleIdentity; // 角色身份，例："IDT_PROJECT",
		@JsonProperty("sex")
		private String sex; // 性别，例："male",
		@JsonProperty("identity_id")
		private String identityId; // 身份信息，例："36072419871225607X",
		@JsonProperty("nickname")
		private String nickname; // 昵称，例："刘**",
		@JsonProperty("id")
		private String id;// 主键ID，例：1033959,
		@JsonProperty("created")
		private String created; // 创建时间，例："2016-05-30 14:32:07",
		@JsonProperty("mobile")
		private String mobile; // 手机号码，例："13927481554",
		@JsonProperty("contact_phones")
		private String contactPhones; // []
		@JsonProperty("state")
		private String state; // 0,
		@JsonProperty("avatar_url")
		private String avatarUrl; // "http://7xnc5a.com2.z0.glb.qiniucdn.com/2016/06/16/13/53/11/00ecd16e-a0c7-40cb-91f7-d3e694805fcd-177X177.jpg",
		@JsonProperty("fullname")
		private String fullName; // 全名，例："刘**",
		@JsonProperty("is_keeper")
		private String isKeeper; // true

		public String getJobCanEdit() {
			return jobCanEdit;
		}

		public void setJobCanEdit(String jobCanEdit) {
			this.jobCanEdit = jobCanEdit;
		}

		public String getUpdated() {
			return updated;
		}

		public void setUpdated(String updated) {
			this.updated = updated;
		}

		public String getRoleIdentity() {
			return roleIdentity;
		}

		public void setRoleIdentity(String roleIdentity) {
			this.roleIdentity = roleIdentity;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public String getIdentityId() {
			return identityId;
		}

		public void setIdentityId(String identityId) {
			this.identityId = identityId;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getCreated() {
			return created;
		}

		public void setCreated(String created) {
			this.created = created;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public String getContactPhones() {
			return contactPhones;
		}

		public void setContactPhones(String contactPhones) {
			this.contactPhones = contactPhones;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getAvatarUrl() {
			return avatarUrl;
		}

		public void setAvatarUrl(String avatarUrl) {
			this.avatarUrl = avatarUrl;
		}

		public String getFullName() {
			return fullName;
		}

		public void setFullName(String fullName) {
			this.fullName = fullName;
		}

		public String getIsKeeper() {
			return isKeeper;
		}

		public void setIsKeeper(String isKeeper) {
			this.isKeeper = isKeeper;
		}

	}

}
