package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT credentialid, url, username, credentialkey, password, userid FROM credentials " +
            "WHERE userid = #{userId}")
    List<Credential> getUserCredentials(Integer userId);

    @Select("SELECT credentialid, url, username, credentialkey, password, userid FROM credentials " +
            "WHERE credentialid = #{credentialId}")
    Credential getCredential(Integer credentialId);

    @Select("SELECT credentialid, url, username, credentialkey, password, userid FROM credentials " +
            "WHERE userid = #{userId} AND url = #{url}")
    Credential getCredentialByUserAndUrl(Integer userId, String url);

    @Insert("INSERT INTO CREDENTIALS (url, username, credentialkey, password, userid) " +
            "VALUES (#{url}, #{username}, #{credentialKey}, #{password}, #{userId})")
    Integer insert(Credential credential);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, credentialkey = #{credentialKey}, password = #{password} " +
            "WHERE credentialid = #{credentialId}")
    Integer update(Credential credential);

    @Delete("DELETE FROM credentials WHERE credentialId = #{credentialId}")
    Integer delete(Integer credentialId);

}
