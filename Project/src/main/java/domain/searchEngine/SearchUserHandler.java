package domain.searchEngine;

import Interfaces.ISearchUser;
import Interfaces.IUser;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchUserHandler implements ISearchUser {
    private static final SearchUserHandler searchUserHandler = new SearchUserHandler();
    private SearchUserHandler(){}
    @Override
    public String getInfoFromSearch(String search, String resultType) {
        if(search != null) {
            Pattern pattern = Pattern.compile("(Brugernavn: )([a-åA-Åø0-9]+)( Rolle: )([a-åA-Åø]+)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(search);
            boolean matchFound = matcher.find();
            if (matchFound) {
                if (resultType.equals("username")) {
                    return matcher.group(2);
                } else {
                    return matcher.group(4);
                }
            }
        }
        return "";
    }

    public static SearchUserHandler getInstance() {
        return searchUserHandler;
    }
}

