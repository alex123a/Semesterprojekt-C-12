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
//  Get the information from a string, see next comment.
    public String getInfoFromSearch(String search, String resultType) {
        if(search != null) {
//          Searches for a patteren of "Brugernavn: <navn> Rolle: <rolle>" without <> and groups them.
            Pattern pattern = Pattern.compile("(Brugernavn: )([a-åA-Åø0-9]+)( Rolle: )([a-åA-Åø]+)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(search);
            boolean matchFound = matcher.find();
//              if the username is wanted, then resultType = username, and the group 2 is returned which is <navn> from the previous comment
//              else group 4 is returned which is <rolle> from the previous comment.
            if (matchFound) {
                if (resultType.equals("username")) {
                    return matcher.group(2);
                } else {
                    return matcher.group(4);
                }
            }
        }
//      If there is not a match, an empty string is returned.
        return "";
    }

    public static SearchUserHandler getInstance() {
        return searchUserHandler;
    }
}

