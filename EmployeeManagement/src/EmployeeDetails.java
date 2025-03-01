package Practice.EmployeeManagement.src;

public class EmployeeDetails {
		
		int id;
		String name;
		int age;
		String deptName;
		String designation;
		String reportingTo;

		EmployeeDetails(){
			
		}
		EmployeeDetails(int id,String name,int age,String deptName,String designation,String reportingTo)
		{
			this.id=id;
			this.name=name;
			this.age=age;
			this.deptName=deptName;
			this.designation=designation;
			this.reportingTo=reportingTo;
		}

}
