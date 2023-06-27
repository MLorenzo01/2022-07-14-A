package it.polito.tdp.nyc.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import it.polito.tdp.nyc.model.Hotspot;
import it.polito.tdp.nyc.model.nta;

public class NYCDao {
	TreeMap<String, nta> idMap;
	
	public List<Hotspot> getAllHotspot(){
		String sql = "SELECT * FROM nyc_wifi_hotspot_locations";
		List<Hotspot> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Hotspot(res.getInt("OBJECTID"), res.getString("Borough"),
						res.getString("Type"), res.getString("Provider"), res.getString("Name"),
						res.getString("Location"),res.getDouble("Latitude"),res.getDouble("Longitude"),
						res.getString("Location_T"),res.getString("City"),res.getString("SSID"),
						res.getString("SourceID"),res.getInt("BoroCode"),res.getString("BoroName"),
						res.getString("NTACode"), res.getString("NTAName"), res.getInt("Postcode")));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	
	public List<String> getAllBorough(){
		String sql = "SELECT DISTINCT n.Borough "
				+ "FROM nyc_wifi_hotspot_locations n";
		List<String> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(res.getString("Borough"));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	public List<nta> getAllNTA(String s){
		idMap = new TreeMap<>();
		String sql = "SELECT DISTINCT n.NTACode, n.NTAName, n.SSID "
				+ "FROM nyc_wifi_hotspot_locations n "
				+ "WHERE n.Borough = ?";
		List<nta> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, s);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				if(idMap.get(res.getString("NTACode")) != null) {
					idMap.get(res.getString("NTACode")).setSsid(res.getString("SSID"));
				}else {
					nta n = new nta(res.getString("NTACode"), res.getString("NTAName"), res.getString("SSID"));
					result.add(n);
					idMap.put(n.getCode(),n);
				}
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
}
