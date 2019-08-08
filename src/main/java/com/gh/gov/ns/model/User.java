package com.gh.gov.ns.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
public class User implements Serializable, UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String userId;
	private String name;
	private String username;
	private String password;
	private String departmentIdentifier;
	private String ministryDescription;
	private String position;
	private boolean isAccountNonExpired;
	private boolean isAccountNonLocked;
	private boolean isCredentialsNonExpired;
	private boolean isEnabled;
	
	/*@OneToOne
	@JoinTable(name="users_role", joinColumns = @JoinColumn(name="user_id", 
	referencedColumnName ="userId"), inverseJoinColumns = 
	@JoinColumn(name="role_id", referencedColumnName="roleId"))
	@JsonManagedReference*/
	/*@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")*/
	private String roleId;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//List<GrantedAuthority> authorities=new ArrayList<>();
		//authorities.add((GrantedAuthority) role);
		return null;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public String toString(){
		return "username: '" + this.username + "', roleId: '" + this.roleId;
	}

}
