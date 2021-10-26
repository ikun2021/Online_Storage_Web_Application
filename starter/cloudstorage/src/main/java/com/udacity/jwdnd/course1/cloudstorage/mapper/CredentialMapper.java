package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Update("update CREDENTIALS SET url=#{url},username=#{username},key=#{key},password=#{password},userid=#{userid} where credentialid=#{credentialid}")
    void editCredential(Credential credential);

    @Insert("insert into CREDENTIALS(url,username,key,password,userid) values(#{url},#{username},#{key},#{password},#{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    void addCredential(Credential credential);


    @Select("select * from CREDENTIALS where userid=#{userid}")
    List<Credential> findAllCredentials(Integer userid);

    @Delete("delete from CREDENTIALS where credentialid=#{credentialid}")
    void deleteById(Integer credentialid);
}
