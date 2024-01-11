package com.learn.jdbc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;

public class DatabaseActions {

	public WebDriver driver;
	public ExtentReports extentReport;
	
	public DatabaseActions() {
		
	}

	public DatabaseActions(WebDriver driver, ExtentReports extentReport) {
		this.driver = driver;
		this.extentReport = extentReport;
	}
	
	public void beforeDatabaseActions() {
		try {
			String createTable = "CREATE TABLE IF NOT EXISTS runautomationdetails LIKE methoddetails;";
			String importTable = "INSERT INTO runautomationdetails SELECT * FROM methoddetails;";
			String truncateTable = "TRUNCATE runautomationdetails;";
			
			Connection connection = getConnection();
			Statement statement = getStatement(connection);
			
			executeQuery(statement, createTable);
			executeQuery(statement, truncateTable);
			executeQuery(statement, importTable);
			
			closeConnection(connection, statement);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void runAutomationFromDatabase() {
		try {
			String selectQuery = "SELECT * FROM runautomationdetails ORDER BY RunTime DESC LIMIT 1;";
			String deleteQuery = "DELETE FROM runautomationdetails ORDER BY RunTime DESC LIMIT 1;";
			
			Connection connection = getConnection();
			Statement statement = getStatement(connection);
			
			ResultSet resultset = getResultsetFromexecuteQuery(statement, selectQuery);
			
			if(resultset.next()) {
				
				Statement deleteStatement = getStatement(connection);
				executeQuery(deleteStatement, deleteQuery);
				
				String packageName = resultset.getString("packagename");
				String className = resultset.getString("classname");
				String methodName = resultset.getString("methodname");
				String methodParameterType = resultset.getString("methodparametertype");
				String methodParameterValue = resultset.getString("methodparametervalues");
				
				Class<?> clazz = Class.forName(packageName + className);
				Constructor<?> constructor = clazz.getConstructor(WebDriver.class, ExtentReports.class);
				Object object = constructor.newInstance(this.driver, this.extentReport);
				
				if(!methodParameterType.isEmpty()) {
					Class[] methodParams = methodParamTypeToClass(methodParameterType);
					Object [] methodValues = methodParamValuesToObject(methodParameterType, methodParameterValue);
					
					Method declaredMethod = clazz.getMethod(methodName, methodParams);
					Parameter[] parameters = declaredMethod.getParameters();
					declaredMethod.invoke(object, methodValues);
				}
				else {
					Method method = clazz.getMethod(methodName);
					method.invoke(object);
				}
				
				runAutomationFromDatabase();
				Thread.sleep(2000);
			}
		} 
		catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Class[] methodParamTypeToClass(String methodParamType) {
		Class[] params = null;
		try {
			String[] split = methodParamType.split(",");
			params = new Class[split.length];
			
			for(int i = 0; i < split.length; i ++) {
				if(split[i].equalsIgnoreCase("integer") || split[i].contains("int")) {
					params[i] = Integer.class;
				}
				else {
					params[i] = String.class;
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}
	
	public Object[] methodParamValuesToObject(String methodParamType, String methodParamValue) {
		Object [] methodValues = null;
		try {
			String[] paramSplit = methodParamType.split(",");
			String[] valueSplit = methodParamValue.split(",");
			methodValues = new Object[valueSplit.length];
			
			for(int i = 0; i < paramSplit.length; i ++) {
				if(paramSplit[i].equalsIgnoreCase("integer") || paramSplit[i].contains("int")) {
					methodValues[i] = Integer.parseInt(valueSplit[i]);
				}
				else {
					methodValues[i] = valueSplit[i];
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return methodValues;
	}
	
	public void runAutomationFromDatabase1() {
		try {
			String selectQuery = "SELECT * FROM runautomationdetails ORDER BY RunTime DESC LIMIT 1;";
			String deleteQuery = "DELETE FROM runautomationdetails ORDER BY RunTime DESC LIMIT 1;";
			
			Connection connection = getConnection();
			Statement statement = getStatement(connection);
			
			ResultSet resultset = getResultsetFromexecuteQuery(statement, selectQuery);
			
			if(resultset.next()) {
				
				Statement statement2 = getStatement(connection);
				executeQuery(statement2, deleteQuery);
				
				String packageName = resultset.getString("packagename");
				String className = resultset.getString("classname");
				String classParametertype = resultset.getString("classparametertype");
				String classParametervalue = resultset.getString("classparametervalues");
				String methodName = resultset.getString("methodname");
				String methodParameterType = resultset.getString("methodparametertype");
				String methodParameterValue = resultset.getString("methodparametervalues");
				
				Class<?> clazz = Class.forName(packageName + className);
				Constructor<?> constructor = clazz.getConstructor(WebDriver.class);
				Object object = constructor.newInstance(this.driver);
				Method method = clazz.getMethod(methodName);
				method.invoke(object);
				
				runAutomationFromDatabase();
			}
			
			closeConnection(connection, statement, resultset);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void connectDatabase() {
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();  
			ResultSet resultset = statement.executeQuery("select * from methoddetails order by RunTime DESC limit 1;");
			
			if(resultset.next()) {
			
				String packageName = resultset.getString(2);
				String className = resultset.getString(3);
				String classParameter = resultset.getString(4);
				String methodName = resultset.getString(5);
				String methodParameter = resultset.getString(6);
				
				System.out.println(packageName);
				
				Class<?> clazz = Class.forName(packageName + className);
				System.out.println(clazz.toString());
				Constructor<?> constructor = clazz.getConstructor(WebDriver.class);
				Object obj = constructor.newInstance(this.driver);
				System.out.println(obj.toString());
				Method method = clazz.getMethod(methodName);
				System.out.println(method.toString());
				method.invoke(obj);
				
				
//				Method method = clazz.getDeclaredMethod("method name");
//				method.invoke(objectToInvokeOn, params);
				
				
				
//				Object myObj = Class.forName(packageName + className).newInstance();
//				System.out.println(myObj.toString());
//				Class clazz = myObj.getClass();
//				System.out.println(clazz.toString());
//				Method method = clazz.getMethod(methodName);
//				System.out.println(method.toString());
//				method.invoke(method, clazz);
				
				
				
//				System.out.println(myclass.toString());
//				Class<? extends Object> class1 = myclass.getClass();
//				Method methods = class1.getMethod(methodName);
//				System.out.println(methods.toString());
//				methods.invoke(methods, myclass);
//				Method method2 = myclass.getMethod(methodName);
//				System.out.println(method2.toString());
			}
			
//			while(resultset.next())  
//				System.out.println(resultset.getString(1)+"  "+resultset.getString(2)+"  "+resultset.getString(3)); 
			closeConnection(connection, statement, resultset);  
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createRunningAutomationTable() {
		try {
			Connection connection = getConnection();
			dropTable(connection);
			Statement statement = connection.createStatement();
			statement.execute("create table MethodDetailsRunning like MethodDetails;");
			statement.execute("insert into MethodDetailsRunning select * from MethodDetails;");
			
			closeConnection(connection, statement);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Automation","root", "MPNsKv!22@@");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public Statement getStatement(Connection connection) {
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return statement;
	}
	
	public void executeQuery(Statement statement, String query) {
		try {
			statement.execute(query);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet getResultsetFromexecuteQuery(Statement statement, String query) {
		ResultSet resultset = null;
		try {
			resultset = statement.executeQuery(query);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return resultset;
	}
	
	public void closeConnection(Connection connection) {
		try {
			connection.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void closeConnection(Connection connection, Statement statement) {
		try {
			statement.close();
			connection.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void closeConnection(Connection connection, Statement statement, ResultSet resultset) {
		try {
			resultset.close();
			statement.close();
			connection.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void dropTable(Connection connection) {
		try {
			Statement statement = connection.createStatement();
			statement.execute("drop table MethodDetailsRunning;");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	public static void main(String[] args) {
//		DatabaseActions da = new DatabaseActions();
//		da.connectDatabase();
////		da.createRunningAutomationTable();
//	}

}
