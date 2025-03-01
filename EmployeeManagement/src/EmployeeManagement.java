package Practice.EmployeeManagement.src;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class EmployeeManagement {
	public static void main(String[] args) throws NumberFormatException, IOException {
		EmployeeManagement m=new EmployeeManagement();
		m.employeeDetailsAdding();
		m.getDetails();
	}
		static Scanner sc=new Scanner(System.in);
		static ArrayList<EmployeeDetails> emp=new ArrayList<EmployeeDetails>();
		static ArrayList<ManagerCount> mc=new ArrayList<ManagerCount>();
		public void getDetails() {
			System.out.println("-------Employee Details--------------");
			System.out.print("1.Show all records\n2.Search records\n3.Manager Report\n4.Reporting Tree\n5.Summary of Record\n6.Exit\nEnter your choice\n");
			int ch=sc.nextInt();
			switch(ch)
			{
			case 1:
				showAllRecords();
				getDetails();
				break;
			case 2:
				searchRecords();
				getDetails();
				break;
			case 3:
				managerReport();
				getDetails();
				break;
			case 4:
				reportingTree();
				getDetails();
				break;
			case 5:
				sumaryOfRecord();
				getDetails();
				break;
			case 6:
				break;
			default:
				System.out.println("Enter correct option");
				getDetails();
				break;
			}
		}
		public void employeeDetailsAdding() throws NumberFormatException, IOException {
			BufferedReader inFile=new BufferedReader(new FileReader("C:\\Users\\balag\\Downloads\\ZOHO-EMPLOYEE-MANAGEMENT-SYSTEM-LLD-master\\ZOHO-EMPLOYEE-MANAGEMENT-SYSTEM-LLD-master\\src\\EmployeeDetails.txt"));
		    String inputline;
		    while ((inputline = inFile.readLine()) != null) {
		        String[] data = inputline.split(",");
		        emp.add(new EmployeeDetails((Integer.parseInt(data[0])),data[1],(Integer.parseInt(data[2])),data[3],data[4],data[5]));
		    }
		    inFile.close();
		}
		public void showAllRecords() {
			System.out.println(" Id             Name        Age        Department           Designation          ReportingTo");
			for(EmployeeDetails em: emp)
			{
				 System.out.println(String.format("%2s %18s %7s %18s %22s %14s", em.id,   em.name,em.age,em.deptName,em.designation,em.reportingTo));
			}
		}
		public void searchRecords() {
			System.out.println("----------------SEARCH RECORDS-------------------\n1. Using Id\n2.Using name\n3.Using Age\n4.Using DepartmentName\n5.Using Designation\n6.ReportingTo\n7.Exit\nEnter your choice:");
			int ch=sc.nextInt();
			switch(ch)
			{
			case 1:
				searchById();
				searchRecords();
				break;
			case 2:
				searchByName();
				searchRecords();
				break;
			case 3:
				searchByAge();
				searchRecords();
				break;
			case 4:
				searchByDepartmentName();
				searchRecords();
				break;
			case 5:
				searchByDesignation();
				searchRecords();
				break;
			case 6:
				searchByManager();
				searchRecords();
				break;
			case 7:
				break;
			default:
				System.out.println("Enter correct option");
				searchRecords();
				break;
			}


		}
		public void searchById() {
			System.out.println("Enter id you want to search:");
			int id=sc.nextInt();
			System.out.println(" Id             Name      Age        Department           Designation          ReportingTo");
			for(EmployeeDetails em:emp)
			{
				if(id==em.id)
				{

					System.out.println(String.format("%2s %18s %6s %18s %16s %20s", em.id,   em.name,em.age,em.deptName,em.designation,em.reportingTo));
				}

			}
		}


		public void searchByName() {
			System.out.println("Enter name you want to search:");
			String name=sc.next();
			sc.nextLine();
			System.out.println("Do you want to search with two compination enter Y/N?");
			String op=sc.next();
			int ans=op.compareTo("Y");
			if(ans==0)
			{
				int ans1=0;
				System.out.println("Enter with which combination you want to enter id ");
				System.out.println("Enter the id:");
				int id1=sc.nextInt();
				System.out.println(" Id             Name      Age        Department           Designation          ReportingTo");
				for(EmployeeDetails em:emp)
				{
					ans1=name.compareTo(em.name);
					if(id1==em.id && ans1==0)
					{

						System.out.println(String.format("%2s %18s %6s %18s %25s %23s", em.id,em.name,em.age,em.deptName,em.designation,em.reportingTo));
						ans1=-1;
						break;
					}
					else
					{
						ans1=-1;
					}
				}

				if(ans1==0)
				{
					System.out.println("No records found");

				}
			}
			else
			{
				System.out.println(" Id     Name       Age        Department        Designation          ReportingTo");
				for(EmployeeDetails em:emp)
				{

					int ans2=name.compareTo(em.name);
					if(ans2==0)
					{

						System.out.print(" "+em.id);
						System.out.print("      "+em.name);
						System.out.print("      "+em.age);
						System.out.print("         "+em.deptName);
						System.out.print("        "+em.designation);
						System.out.println("                 "+em.reportingTo);
						System.out.println();
						break;

					}


				}


			}

		}
		public void searchByAge() {
			System.out.println("Enter the age you want to search");
			int age=sc.nextInt();
			System.out.println("If you want to search with two combination means enter Y/N?");
			String op=sc.next();
			int ans=op.compareTo("Y");
			int fi=1;
			if(ans==0)
			{
				System.out.println("Enter id:");
				int id=sc.nextInt();

				for(EmployeeDetails em:emp) {
					if(age==em.age &&   id==em.id)
					{
						System.out.print(" "+em.id);
						System.out.print("      "+em.name);
						System.out.print("      "+em.age);
						System.out.print("         "+em.deptName);
						System.out.print("        "+em.designation);
						System.out.println("                 "+em.reportingTo);
						System.out.println();
						fi=0;
						break;

					}
				}
			}
			else
			{
				for(EmployeeDetails em:emp) {
					if(age==em.age)
					{

						System.out.print(" "+em.id);
						System.out.print("      "+em.name);
						System.out.print("      "+em.age);
						System.out.print("         "+em.deptName);
						System.out.print("        "+em.designation);
						System.out.println("                 "+em.reportingTo);
						System.out.println();
						fi=0;
						break;

					}
				}
			}
			if(fi!=0)
			{
				System.out.println("No such record found");
			}
		}


		public void searchByDepartmentName() {
			System.out.println("Enter the Department name you want to search");
			String dName=sc.next();
			System.out.println("If you want to search with two combination enter Y or N");
			String op1=sc.next();
			int ans=op1.compareTo("Y");
			int fi=0;
			if(ans!=0)
			{
				for(EmployeeDetails em: emp)
				{
					int op=dName.compareTo(em.deptName);
					if(op==0) {
						System.out.print(" "+em.id);
						System.out.print("      "+em.name);
						System.out.print("      "+em.age);
						System.out.print("         "+em.deptName);
						System.out.print("        "+em.designation);
						System.out.println("                 "+em.reportingTo);
						System.out.println();
						fi=1;

					}
				}
			}

		

		else
		{
			System.out.println("Enter Id:");
			int id=sc.nextInt();
			for(EmployeeDetails em: emp)
			{
				int op=dName.compareTo(em.deptName);
				if(op==0 & em.id==id) {
					System.out.print(" "+em.id);
					System.out.print("      "+em.name);
					System.out.print("      "+em.age);
					System.out.print("         "+em.deptName);
					System.out.print("        "+em.designation);
					System.out.println("                 "+em.reportingTo);
					System.out.println();
					fi=1;

				}
			

		}
		}
			if(fi!=1)
			{
				System.out.println("No such record found");
			}
		}

		public void searchByDesignation() {
			System.out.println("Enter the Designation name you want to search");
			String designation=sc.next();
			System.out.println("If you want to search with two combination typw Y/N?");
			String op=sc.next();
			int op1=op.compareTo("Y");
			int fi=0;
			if(op1==0)
			{
				System.out.println("Enter Id:");
				int id=sc.nextInt();
			for(EmployeeDetails em: emp)
			{
				int op2=designation.compareTo(em.designation);
				if(op2==0&& id==em.id) {
					System.out.print(" "+em.id);
					System.out.print("      "+em.name);
					System.out.print("      "+em.age);
					System.out.print("         "+em.deptName);
					System.out.print("        "+em.designation);
					System.out.println("                 "+em.reportingTo);
					System.out.println();
					fi=1;

				}
			}
		}
			else
			{
				for(EmployeeDetails em: emp)
				{
					int op2=designation.compareTo(em.designation);
					if(op2==0) {
						System.out.print(" "+em.id);
						System.out.print("      "+em.name);
						System.out.print("      "+em.age);
						System.out.print("         "+em.deptName);
						System.out.print("        "+em.designation);
						System.out.println("                 "+em.reportingTo);
						System.out.println();
						fi=1;

					}
				}
			}
			if(fi==0)
			{
				System.out.println("No records found");
			}


		}

		public void searchByManager() {

			System.out.println("Enter the manager  name you want to search");
			String mName=sc.next();
			System.out.println("If you want to search with two combinations enter Y/N?");
			String op1=sc.next();
			int fi=0;
			int op2=op1.compareTo("Y");
			if(op2==0)
			{
				System.out.println("Enter id:");
				int id=sc.nextInt();
			for(EmployeeDetails em: emp)
			{
				int op=mName.compareTo(em.designation);
				if(op==0 && id==em.id) {
					System.out.print(" "+em.id);
					System.out.print("      "+em.name);
					System.out.print("      "+em.age);
					System.out.print("         "+em.deptName);
					System.out.print("        "+em.designation);
					System.out.println("                 "+em.reportingTo);
					System.out.println();
					fi=1;

				}
			}
			}
			else
			{
				for(EmployeeDetails em: emp)
				{
					int op=mName.compareTo(em.designation);
					if(op==0) {
						System.out.print(" "+em.id);
						System.out.print("      "+em.name);
						System.out.print("      "+em.age);
						System.out.print("         "+em.deptName);
						System.out.print("        "+em.designation);
						System.out.println("                 "+em.reportingTo);
						System.out.println();
						fi=1;

					}
				}
			}
			if(fi==0)
			{
				System.out.println("No records found");
			}

		}




		public void managerReport() {
			System.out.println("Enter manager name:");
			String managerName=sc.next();
			System.out.println("Employees working under manager "+ managerName +" are");
			for(EmployeeDetails em:emp)
			{
				int op=managerName.compareTo(em.reportingTo);
				if(op==0)
				{
					System.out.println(em.name);

				}

			}
		}

		public void reportingTree()
		{
			System.out.println("Enter employee name");
			String name=sc.next();
			String temp=name;
			ArrayList<String> names=new ArrayList<String>();
			int an=1;
			while(an!=0)
			{
				for(EmployeeDetails em: emp)
				{
					int op=temp.compareTo(em.name);
					if(op==0)
					{
						names.add(em.reportingTo);
						temp=em.reportingTo;
					}
					an=temp.compareTo("null");
				}
			}
			System.out.print(name);
			for(String s: names)
			{
				int a=s.compareTo("null");

				System.out.print("-->");
				if(a!=0)
				{
					System.out.print(s);
				}

			}
			System.out.println();
		}

		public void sumaryOfRecord() {
			System.out.println("1.Department summary");
			System.out.println("2.Designation Summary");
			System.out.println("3.Managers Summary");
			System.out.println("4.EmployeeManagement menu");
			System.out.println("Enter your choice:");
			int ch=sc.nextInt();
			switch(ch)
			{
			case 1:
				departmentSumary();
				sumaryOfRecord();
				break;
			case 2:
				designationSummary();
				sumaryOfRecord();
				break;

			case 3:
				managerSumary();
				sumaryOfRecord();
				break;

			case 4:
				getDetails();
				break;
			default:
				System.out.println("Enter correct option");
				break;


			}
		}
		public void departmentSumary()
		{
			int managementCount=0;
			int hrCount=0;
			int financeCount=0;
			int pMCount=0;
			int pDcount=0;
			int pTCount=0;
			for(EmployeeDetails em:emp)
			{
				int d1=("Management").compareTo(em.deptName);
				if(d1==0)
				{
					managementCount++;
				}
				int d2=("HR").compareTo(em.deptName);
				if(d2==0)
				{

					hrCount++;
				}
				int d3=("Finance").compareTo(em.deptName);
				if(d3==0)
				{
					financeCount++;
				}
				int d4=("ProductManagement").compareTo(em.deptName);
				if(d4==0)
				{
					pMCount++;
				}
				int d5= ("ProductDevelopment").compareTo(em.deptName);
				if(d5==0)
				{
					pDcount++;
				}
				int d6=("ProductTesting").compareTo(em.deptName);
				if(d6==0)
				{
					pTCount++;
				}


			}
			System.out.println("Number of employees working in Management department:"+managementCount);
			System.out.println("Number of employees working in HR department:"+hrCount);
			System.out.println("Number of employees working in Finance department:"+financeCount);
			System.out.println("Number of employees working in Product Management department:"+pMCount);
			System.out.println("Number of employees working in Product Development department:"+pDcount);
			System.out.println("Number of employees working in Product Testing department:"+pTCount);
		}
		public void designationSummary()
		{
			int ceoCount=0;
			int hrManagerCount=0;
			int fmCount=0;
			int devCount=0;
			int hrLeadCount=0;
			int hrAssCount=0;
			int techLeadCount=0;
			int fAssCount=0;
			int sysdevCount=0;
			int qaLeadCount=0;
			for(EmployeeDetails em:emp)
			{
				int d1=("CEO").compareTo(em.designation);
				if(d1==0)
				{
					ceoCount++;
				}
				int d2=("HRManager").compareTo(em.designation);
				if(d2==0)
				{
					hrManagerCount++;	
				}
				int d3=("FinanceManager").compareTo(em.designation);
				if(d3==0)
				{
					fmCount++;	
				}
				int d4=("DevManager").compareTo(em.designation);
				if(d4==0)
				{
					devCount++;
				}
				int d5=("HRLead").compareTo(em.designation);
				if(d5==0)
				{
					hrLeadCount++;
				}
				int d6=("HRAssociate").compareTo(em.designation);
				if(d6==0)
				{
					hrAssCount++;
				}
				int d7=("FinanceAssociate").compareTo(em.designation);
				if(d7==0)
				{
					fAssCount++;
				}

				int d8=("TechLead").compareTo(em.designation);
				if(d8==0)
				{
					techLeadCount++;
				}
				int d9=("SystemDeveloper").compareTo(em.designation);
				if(d9==0)
				{
					sysdevCount++;
				}
				int d10=("QALead").compareTo(em.designation);
				if(d10==0)
				{
					qaLeadCount++;
				}
			}
			System.out.println("Number of employees working as CEO:"+ceoCount);
			System.out.println("Number of Employees as HR Manager:"+hrManagerCount);
			System.out.println("Number of employees working as Finance Manager:"+fmCount);
			System.out.println("Number of Employees as Dev Manager:"+devCount);
			System.out.println("Number of employees working as HR Lead:"+hrLeadCount);
			System.out.println("Number of Employees as HR Associate:"+hrAssCount);
			System.out.println("Number of employees working as Finance Associate:"+fAssCount);
			System.out.println("Number of Employees as Tech Lead:"+techLeadCount);
			System.out.println("Number of employees working as System Developer:"+sysdevCount);
			System.out.println("Number of Employees as QA Lead:"+qaLeadCount);
		}
		public void managerSumary()
		{
			
			int count1=0;
			int count2=0;
			int count3=0;
			int count4=0;
			int count5=0;
			int count6=0;
			for(EmployeeDetails em:emp)
			{
				int d1=("Sriram").compareTo(em.reportingTo);
				if(d1==0)
				{
					count1++;
				}
				int d2=("Mukund").compareTo(em.reportingTo);
				if(d2==0)
				{
					count2++;
				}
				int d3=("MohammadRafi").compareTo(em.reportingTo);
				if(d3==0)
				{
					count3++;
				}
				int d4=("Sebastian").compareTo(em.reportingTo);
				if(d4==0)
				{
					count4++;
				}
				int d5=("Aashritha").compareTo(em.reportingTo);
				if(d5==0)
				{
					count5++;	
				}
				int d6=("Ramachandran").compareTo(em.reportingTo);
				if(d6==0)
				{
					count6++;
				}
			}
			mc.add(new ManagerCount(count1,"Sriram"));
			mc.add(new ManagerCount(count2,"Mukund"));
			mc.add(new ManagerCount(count3,"Mohammad Rafi"));
			mc.add(new ManagerCount(count4,"Sebastian"));
			mc.add(new ManagerCount(count5,"Aashritha"));
			mc.add(new ManagerCount(count6,"Ramachandran"));
			
			for(ManagerCount m:mc)
			{
				System.out.println("Number of employees working under "+ m.mName+" is: "+m.count);
				
			}
		}

	}