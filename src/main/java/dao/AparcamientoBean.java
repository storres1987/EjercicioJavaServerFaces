package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.io.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.annotation.Resource;

import vo.*;

@ManagedBean(name = "customer")
@SessionScoped
public class AparcamientoBean implements Serializable {
	
	@Resource(name="jdbc/oracle")
	private DataSource ds;
	private ResultSet rs;

	public AparcamientoBean() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Customer> getallAparcamientos() throws SQLException {
		if (ds == null)
			throw new SQLException("No se a realizado la conexion");
		Connection con = ds.getConnection();
		if (con == null)
			throw new SQLException("Can't get database connection");
		PreparedStatement ps = con
				.prepareStatement("select * from APARCAMIENTOSPERDISCAP");
		ResultSet result = ps.executeQuery();
		ArrayList<Customer> list = new ArrayList<Customer>();
		while (result.next()) {

			Customer aparcamiento = new Customer();
			aparcamiento.setId(result.getInt("id"));
			aparcamiento.setLatitud(result.getDouble("latitud"));
			aparcamiento.setLongitud(result.getDouble("longitud"));
			aparcamiento.setTitulo(result.getString("titulo"));
			aparcamiento.setIcono(result.getString("icono"));
			list.add(aparcamiento);
		}

		return list;
	}
}
