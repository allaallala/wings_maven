package edu.msstate.nsparc.wings.integration.enums.SqlQueries;

import framework.CommonFunctions;

import java.nio.file.Path;
import java.nio.file.Paths;

public interface ISqlQuery {

    default String getQuery(Path path){
        return CommonFunctions.getQueryFromFile(Paths.get(System.getProperty("user.dir"), "src", "test", "resources", "sqlQueries")
                .resolve(path));
    }
}
