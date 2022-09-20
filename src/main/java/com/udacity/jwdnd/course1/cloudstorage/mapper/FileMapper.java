package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT fileId, filename, contenttype filesize FROM files WHERE userid = #{userId}")
    List<File> getUserFiles(Integer userId);

    @Select("SELECT fileId, filename, contenttype, filesize, userid, filedata FROM files WHERE fileId = #{fileId}")
    File getFile(Integer fileId);

    @Select("SELECT fileId, filename FROM files WHERE filename = #{filename} AND userid = #{userId}")
    File getFileByNameAndUserId(String filename, Integer userId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " +
            "VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insert(File file);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileId}")
    Integer delete(Integer fileId);

}
