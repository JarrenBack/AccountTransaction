import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class Controller {
	private Model model = new Model();
	
	public DefaultTableModel getCurrentStateOfDB() {
		
		ResultSet result = model.getCurrentState();
		DefaultTableModel records = null;
		try {
			records = buildTableModel (result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return records;
	}
	
	public boolean transfer (int accountFrom, int accountTo, int amount) {
		return model.transfer(accountFrom, accountTo, amount);
	}
	
	private DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
		
	    java.sql.ResultSetMetaData metaData = rs.getMetaData();

	    // names of columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) {
	        columnNames.add(metaData.getColumnName(column));
	    }

	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);
	    }

	    return new DefaultTableModel(data, columnNames);
	}
}
