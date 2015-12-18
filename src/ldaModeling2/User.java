package ldaModeling2;

import java.util.Map;
import java.util.TreeMap;

/**
 * a user set
 * 
 * @author hty
 *
 */
public class User {
	
	Map<String, Integer> user2idMap;
    String[] id2userMap;
	
    public User(int userNum){
    	user2idMap = new TreeMap<String, Integer>();
    	id2userMap = new String[userNum];
    }
    
    public String getUser(int id)
    {
        return id2userMap[id];
    }
    
    public Integer getId(String user){
    	return getId(user,false);
    }
    
    public Integer getId(String user, boolean create){
    	Integer id = user2idMap.get(user);
        if (!create) return id;
        if (id == null)
        {
            id = user2idMap.size();
        }
        user2idMap.put(user, id);
        if (id2userMap.length - 1 < id)
        {
            resize(user2idMap.size() * 2);
        }
        id2userMap[id] = user;

        return id;
    }
    
    private void resize(int n)
    {
        String[] nArray = new String[n];
        System.arraycopy(id2userMap, 0, nArray, 0, id2userMap.length);
        id2userMap = nArray;
    }
    
    public int size()
    {
        return user2idMap.size();
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < id2userMap.length; i++)
        {
            if (id2userMap[i] == null) break;
            sb.append(i).append("=").append(id2userMap[i]).append("\n");
        }
        return sb.toString();
    }
    
}
