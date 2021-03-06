package com.amazonaws.lambda.demo.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//import com.amazonaws.lambda.demo.model.Constant;
import com.amazonaws.lambda.demo.model.Segment;
import com.amazonaws.lambda.demo.model.SegmentRemote;

public class SegmentsDAO {
	java.sql.Connection conn;
	public static final String SITE_URL = "https://3733lydia.s3.us-east-2.amazonaws.com/segments/";

    public SegmentsDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }
    
	public Segment getSegment(String name) throws Exception {
	        
        try {
            Segment segment = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM segments WHERE name=?;");
            ps.setString(1,  name);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                segment = generateSegment(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return segment;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting segment: " + e.getMessage());
        }
    }
	
	public Segment getSegmentByFilePath(String filePath) throws Exception {
        
        try {
            Segment segment = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM segments WHERE originFilePath=?;");
            ps.setString(1,  filePath);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                segment = generateSegment(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return segment;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting segment: " + e.getMessage());
        }
    }
	
	
	public boolean addSegment(Segment segment) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM segments WHERE id = ?;");
            ps.setString(1, segment.getID());
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                Segment s = generateSegment(resultSet);
                resultSet.close();
                return false;
            }
            
            ps = conn.prepareStatement("INSERT INTO segments values(?,?,?,?,?,?,?);");
            ps.setString(1,  segment.getID());
            ps.setString(2,  segment.getName());
            ps.setString(3, segment.getOriginFilePath() + ".ogg");
            ps.setString(4,  segment.getOriginSite());
            ps.setBoolean(5,  segment.isRemotelyAvailable());
            ps.setString(6,  segment.getCharacter());
            ps.setString(7,  segment.getSentence());
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert segment: " + e.getMessage());
        }
    }

    public boolean addExternalSegment(SegmentRemote sr, String originSite) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM segments WHERE originFilePath = ?;");
            ps.setString(1, sr.getUrl());
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                Segment s = generateSegment(resultSet);
                resultSet.close();
                return false;
            }
            String newID = Segment.getNewID();
            ps = conn.prepareStatement("INSERT INTO segments values(?,?,?,?,?,?,?);");
            ps.setString(1,  newID);
            ps.setString(2,  sr.getCharacter() + newID.substring(0, 5));
            ps.setString(3, sr.getUrl());
            ps.setString(4,  originSite);
            ps.setBoolean(5,  false);
            ps.setString(6,  sr.getCharacter());
            ps.setString(7,  sr.getText());
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert segment: " + e.getMessage());
        }
    }
	
	public boolean deleteSegment(Segment segment) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM segments WHERE id = ?;");
            ps.setString(1, segment.getID());
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to delete segment: " + e.getMessage());
        }
    }
	
	public boolean deleteSegment(String id) throws Exception {
        try {
        	PreparedStatement ps = conn.prepareStatement("DELETE FROM playlistEntries WHERE segmentID = ?;");
        	//PreparedStatement ps = conn.prepareStatement("SELECT id FROM segments WHERE name = ? and originSite = ?;");
        	//PreparedStatement ps = conn.prepareStatement("SELECT * FROM segments;");

        	ps.setString(1, id);
            ps.executeUpdate();
            ps.close();
        	
            PreparedStatement ps1 = conn.prepareStatement("DELETE FROM segments WHERE id = ?;");
            ps1.setString(1, id);
            int numAffected = ps1.executeUpdate();
            ps1.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to delete segment: " + e.getMessage());
        }
    }
	
	public List<Segment> getAllSegments() throws Exception {
        
        List<Segment> allSegments = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM segments ORDER BY name";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Segment s = generateSegment(resultSet);
                allSegments.add(s);
            }
            resultSet.close();
            statement.close();
            return allSegments;

        } catch (Exception e) {
            throw new Exception("Failed in getting books: " + e.getMessage());
        }
    }
	
	public List<Segment> getAllLocalSegments() throws Exception {
	    
	    List<Segment> allSegments = new ArrayList<>();
	    try {
	        PreparedStatement ps = conn.prepareStatement("SELECT * FROM segments WHERE originSite = ?");
	        ps.setString(1, SegmentsDAO.SITE_URL);
	        ResultSet resultSet = ps.executeQuery();
	
	        while (resultSet.next()) {
	            Segment s = generateSegment(resultSet);
	            allSegments.add(s);
	        }
	        resultSet.close();
	        ps.close();
	        return allSegments;
	
	    } catch (Exception e) {
	        throw new Exception("Failed in getting books: " + e.getMessage());
	    }
	}

	public List<Segment> getAllRemoteSegments() throws Exception {
    
	    List<Segment> allSegments = new ArrayList<>();
	    try {
	        PreparedStatement ps = conn.prepareStatement("SELECT * FROM segments WHERE originSite <> ?");
	        ps.setString(1, SegmentsDAO.SITE_URL);
	        ResultSet resultSet = ps.executeQuery();
	
	        while (resultSet.next()) {
	            Segment s = generateSegment(resultSet);
	            allSegments.add(s);
	        }
	        resultSet.close();
	        ps.close();
	        return allSegments;
	
	    } catch (Exception e) {
	        throw new Exception("Failed in getting books: " + e.getMessage());
	    }
	}
	
	public List<SegmentRemote> exportAvailableSegments() throws Exception {
	    
	    List<SegmentRemote> allSegmentRemotes = new ArrayList<>();
	    try {
	        PreparedStatement ps = conn.prepareStatement("SELECT * FROM segments WHERE remotelyAvailable = True and originSite = ?;");
	        ps.setString(1, SegmentsDAO.SITE_URL);
	        ResultSet resultSet = ps.executeQuery();
	
	        while (resultSet.next()) {
	            SegmentRemote s = generateSegmentRemote(resultSet);
	            allSegmentRemotes.add(s);
	        }
	        resultSet.close();
	        ps.close();
	        return allSegmentRemotes;
	
	    } catch (Exception e) {
	        throw new Exception("Failed in getting remotely available segments: " + e.getMessage());
	    }
	}
	
	public List<Segment> getAllSegmentsFromPlaylist(String playlistName) throws Exception {
        
        List<Segment> allSegments = new ArrayList<>();
        try {
        	PreparedStatement ps = conn.prepareStatement("SELECT * FROM segments WHERE id in "
            		+ "(SELECT segmentID FROM innodb.playlistEntries WHERE playlistName=? ORDER BY playlistEntryNumber)");
        	ps.setString(1, playlistName);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Segment s = generateSegment(resultSet);
                allSegments.add(s);
            }
            resultSet.close();
            return allSegments;

        } catch (Exception e) {
            throw new Exception("Failed in getting segments for playlist "+ playlistName + ": " + e.getMessage());
        }
	}
	
	public boolean toggleRemotelyAvailable(String name) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE segments SET remotelyAvailable = NOT remotelyAvailable WHERE id = ? AND originSite = ?;");
            ps.setString(1, name);
            ps.setString(2, SITE_URL);
            int numAffected = ps.executeUpdate();
            ps.close();
            return numAffected == 1;

        } catch (Exception e) {
            throw new Exception("Failed in getting books: " + e.getMessage());
        }
	}
	
	private Segment generateSegment(ResultSet resultSet) throws Exception {
		String id = resultSet.getString("id");
		String name = resultSet.getString("name");
		String originFilePath = resultSet.getString("originFilePath");
		String originSite = resultSet.getString("originSite");
		boolean remotelyAvailable = resultSet.getBoolean("remotelyAvailable");
		String character = resultSet.getString("character");
		String sentence = resultSet.getString("sentence");
	    //Construct a segment
	    return new Segment(id, name, character, sentence, originFilePath, originSite, remotelyAvailable);
	}
	
	private SegmentRemote generateSegmentRemote(ResultSet resultSet) throws Exception{
		String url = resultSet.getString("originFilePath");
		String character = resultSet.getString("character");
		String text = resultSet.getString("sentence");
		
		return new SegmentRemote(url, character, text);
	}
}
