import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.SimpleDateFormat;
import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.util.Util;

class DB
{
	Connection con;
	ResultSet rs;
	PreparedStatement psi,psm,psd;
	PreparedStatement psno,psyr,pstid,psyt,psmn,pss,pst;
	PreparedStatement pscount;
	
	DB()
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
        		Connection con = //ENTER THE DETAILS;
			psi= con.prepareStatement("insert into student values(?,?,?,?,?,?,?,?,?)");
			psm= con.prepareStatement("update student set sacadyr=?,strdid=?,strnm=?,sname=?,smobile=?,sbirthdt=?,semail=?,state=? where sprnno=?");
			psd= con.prepareStatement("update student set state=0 where sprnno=?");
			psno=con.prepareStatement("select * from student where sprnno=?");
			psyr=con.prepareStatement("select * from student where sacadyr=?");
			pstid=con.prepareStatement("select * from student where strdid=?");
			psyt=con.prepareStatement("select * from student where strdid=? and sacadyr=?");
			psmn=con.prepareStatement("select * from student where smobile=?");
			pss=con.prepareStatement("select * from student");
			pst=con.prepareStatement("select * from trade where tid=?");
			pscount=con.prepareStatement("select * count(sprnno) where sacadyr=? and strdid=?");
		}
		catch(Exception e){
			System.out.println(""+e);
		}
	}

	void insert(String no, int yr, int id, String tnm,String snm,String mno,Date bd,String mail, int state )
	{
		try
		{
			psi.setString(1,no);
			psi.setInt(2,yr);
			psi.setInt(3,id);
			psi.setString(4,tnm);
			psi.setString(5,snm);
			psi.setString(6,mno);
			psi.setDate(7,bd);
			psi.setString(8,mail);
			psi.setInt(9,1);
			psi.executeUpdate();
			
		}
		catch(Exception e){}	
	}

	void modify(String no, int yr, int id, String tnm,String snm,String mno,Date bd,String mail)	
	{

		try
		{
			psm.setInt(1,yr);
			psm.setInt(2,id);
			psm.setString(3,tnm);
			psm.setString(4,snm);
			psm.setString(5,mno);
			psm.setDate(6,bd);
			psm.setString(7,mail);
			psm.setString(8,no);
			psm.executeUpdate();
	
		}
		catch(Exception e){}
	}

	void delete(String no)
	{
		
		try
		{
			psd.setString(1,no);
			psd.executeUpdate();
		}
		catch(Exception e){}
	}

	void searchPRN(String no)
	{
		try
		{
			psno.setString(1,no);
			rs=psno.executeQuery();
		}
		catch(Exception e){}
	}

	void searchYear(int yr)
	{
		try
		{
			psyr.setInt(1,yr);
			rs=psyr.executeQuery();
		}
		catch(Exception e){}
	}

	void searchTid(int id)
	{
		try
		{
			pstid.setInt(1,id);
			rs=pstid.executeQuery();
		}
		catch(Exception e){}
	}

	void searchYrTr(int yr, int id)
	{
		try
		{
			psyt.setInt(1,id);
			psyt.setInt(2,yr);
			rs=psyt.executeQuery();
		}
		catch(Exception e){}
	}

	void searchMbNo(String mno)
	{
		try
		{
			psmn.setString(1,mno);
			rs=psmn.executeQuery();
		}
		catch(Exception e){}
	}

	void displayStudent()
	{
		try
		{
			rs=pss.executeQuery();
		}
		catch(Exception e){}
	}

	void searchTr(int id)
	{
		try
		{
			pst.setInt(1,id);
			rs=pst.executeQuery();
		}
		catch(Exception e){
		}
	}

	int count(int yr, int id)
	{
		int count=0;
		try
		{
			pscount.setInt(1,yr);
			pscount.setInt(2,id);
			rs=pscount.executeQuery();
			if(rs.next())
			{
				count = rs.getInt(1);
			}
		}
		catch(Exception e){}
		return count;
	}			
}


class AddRecord extends JDialog implements ActionListener,FocusListener
{
	JLabel lyr,lid,lnm,lsnm,lbd,lmn,lmail;
	JButton ba,bb;
	JComboBox yr;
	JTextField tid,ttnm,tsnm,tmail;
	NTextField tmn; // created the seprate file for mobile as input. 
	int cnt,id,ayr;
	NJDatePicker bd; // created the seprate file for picking date.
	String tnm,snm,pno,mail,mno;
	DB ref;
	Date bdate;
	
	AddRecord(JFrame frm, String title, boolean state, DB ref)
	{
		super(frm,title,state);
		this.ref=ref;
		
		lyr= new JLabel("Academic Year");
		lid= new JLabel("Trade ID");
		lnm= new JLabel("Trade Name");
		lsnm= new JLabel("Student Name");
		lbd= new JLabel("Birth Date");
		lmn= new JLabel("Mobile Number");
		lmail= new JLabel("Mail ID");

		ba=new JButton("Add");
		bb=new JButton("Back");
	
		ba.addActionListener(this);
		bb.addActionListener(this);
		
		tid=new JTextField(5);
		tid.addFocusListener(this);
		
		ttnm=new JTextField(40);
		ttnm.setEditable(false);
	
		tsnm=new JTextField(40);
		tmn=new NTextField(15);
		
		tmail=new JTextField(50);
		yr=new JComboBox();
		
		for(int i=2000;i<=2033;i++)
		{
			yr.addItem(""+i);
		}
		 
		bd=new NJDatePicker();
		
		setLayout(null);
		
		lsnm.setBounds(50,50,100,30);
		lyr.setBounds(50,90,100,30);
		lid.setBounds(50,130,100,30);
		lnm.setBounds(50,170,100,30);
		lbd.setBounds(50,210,100,30);
		lmn.setBounds(50,250,100,30);
		lmail.setBounds(50,290,100,30);

		tsnm.setBounds(170,50,100,30);
		yr.setBounds(170,90,100,30);
		tid.setBounds(170,130,100,30);
		ttnm.setBounds(170,170,100,30);
		bd.setBounds(170,210,200,30);
		tmn.setBounds(170,250,100,30);
		tmail.setBounds(170,290,100,30);
		
		ba.setBounds(50,330,100,30);
		bb.setBounds(170,330,100,30);
		
		add(lsnm);
		add(tsnm);
		add(lyr);
		add(yr);
		add(lid);
		add(tid);
		add(lnm);
		add(ttnm);
		add(lbd);
		add(bd);
		add(lmn);
		add(tmn);
		add(lmail);
		add(tmail);
		add(ba);
		add(bb);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(400,400);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		JButton b=(JButton)e.getSource();
		if(b==ba)
		{
			try
			{
				snm=tsnm.getText();
				tnm=ttnm.getText();
				ayr=Integer.parseInt(""+yr.getSelectedItem());
				mail=tmail.getText();
				mno=tmn.getText();
				bdate=(Date)bd.getSqlDate();
				cnt=ref.count(ayr, id);
				cnt=cnt+1;
				if(id<10)
				{
					pno=""+ayr+"0"+id+cnt;
				}
				else
				{
					pno=""+ayr+id+cnt;
				}
				ref.insert(pno,ayr,id,tnm,snm,mno,bdate,mail,1);
			}
			catch(Exception e2){}
		}
		setVisible(false);
		JOptionPane.showMessageDialog(null,"Record Added Succesfully");
	}

	@Override
	public void focusGained(FocusEvent e) {}

	@Override
	public void focusLost(FocusEvent e) 
	{
		try
		{
			id=Integer.parseInt(tid.getText());
		}
		catch(Exception e1)
		{
		}
		try
		{
			ref.searchTr(id);
			ResultSet rs=ref.rs;
			if(rs.next())
			{
				ttnm.setText(rs.getString(2));
			}
			else
			{
				tid.requestFocus();
				return;
			}
		}
		catch(Exception e2)
		{

		}
						
	}

}


class ModRecord extends JDialog implements ActionListener,FocusListener
{
	JLabel lyr,lid,lnm,lsnm,lbd,lmn,lmail,lpnr;
	JButton ba,bb;
	JComboBox yr,pnr;
	JTextField tid,ttnm,tsnm,tmail;
	NTextField tmn;
	int cnt,id,ayr;
	NJDatePicker bd;
	String tnm,snm,pno,mail,mno,dpno;
	DB ref;
	Date bdate;
	SimpleDateFormat dateFormat2=new SimpleDateFormat("dd-mm-yyyy");
	ModRecord(JFrame frm,String title,boolean state,DB ref)
	{
		super(frm,title,state);
		this.ref=ref;
		lpnr=new JLabel("PNR No");
		lyr=new JLabel("AYear");
		lid=new JLabel("Trade ID");
		lbd = new JLabel("Birth date");
		lnm=new JLabel("Trade Name");
		lmn = new JLabel("Mobile number");
		lsnm=new JLabel("Student Name");
		lmail=new JLabel("Mail ID");
		ba=new JButton("Mod");
		bb=new JButton("Back");
		ba.addActionListener(this);
		bb.addActionListener(this);
		tid=new JTextField(5);
		tid.addFocusListener(this);
		tsnm=new JTextField(40);
		ttnm=new JTextField(40);
		ttnm.setEditable(false);
		ttnm=new JTextField(40);
		tmn=new NTextField(15);
		tmail=new JTextField(40);
		yr=new JComboBox();
		pnr=new JComboBox();
		ref.displayStudent();
		ResultSet rs=ref.rs;
		try 
		{
			while(rs.next())
			{
				String s=rs.getString(1);
				if(rs.getInt(9)==1)
					pnr.addItem(s);
			}	//adds each student pnr to combo box
		} catch (Exception e) {}
		pnr.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				dpno=""+pnr.getSelectedItem();
				ref.searchPRN(dpno);
				ResultSet rs=ref.rs;
					try 
					{
						if(rs.next())
						{
							pno=rs.getString(1);
							ayr=rs.getInt(2);
							id=rs.getInt(3);
							tnm=rs.getString(4);
							snm=rs.getString(5);
							mno=rs.getString(6);
							bdate=rs.getDate(7);
							String s=dateFormat2.format(bdate);
							bd.dateField.setText(s);
							mail=rs.getString(8);
							yr.setSelectedItem(""+ayr);
							tsnm.setText(snm);
							tid.setText(""+id);
							ttnm.setText(tnm);
							tmn.setText(mno);
							tmail.setText(mail);
						}	
					}
					 catch (Exception e6) {}
			}
		});
		for(cnt=2000;cnt<=2033;cnt++)
			yr.addItem(""+cnt);
		bd=new NJDatePicker();
		bd.dp.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				bdate=(Date)bd.getSqlDate();
			}
		});
		setLayout(null);
		lpnr.setBounds(50,50,100,30);
		lsnm.setBounds(50, 90, 100, 30);
		lyr.setBounds(50, 130, 100, 30);
		lid.setBounds(50, 170, 100, 30);
		lnm.setBounds(50, 210, 100, 30);
		lbd.setBounds(50, 250, 100, 30);
		lmn.setBounds(50, 290, 100, 30);
		lmail.setBounds(50, 330, 100, 30);

		pnr.setBounds(170, 50, 100, 30);
		tsnm.setBounds(170, 90, 100, 30);
		yr.setBounds(170, 130, 100, 30);
		tid.setBounds(170, 170, 100, 30);
		ttnm.setBounds(170, 210, 100, 30);
		bd.setBounds(170, 250, 200, 30);
		tmn.setBounds(170, 290, 100, 30);
		tmail.setBounds(170, 330, 100, 30);

		ba.setBounds(50, 380, 100, 30);
		bb.setBounds(200, 380, 100, 30);
		ttnm.setEditable(false);
		add(lpnr);
		add(pnr);
		add(lsnm);
		add(tsnm);
		add(lyr);
		add(yr);
		add(lid);
		add(tid);
		add(lnm);
		add(ttnm);
		add(lbd);
		add(bd);
		add(lmn);
		add(tmn);
		add(lmail);
		add(tmail);
 		add(ba);
		add(bb);
 		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(400, 480);
		setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
		JButton b=(JButton)e.getSource();
		if(b==ba)
		{
			try 
			{
				snm=tsnm.getText();		//student name
				tnm=ttnm.getText();		//trade name
				ayr=Integer.parseInt(""+yr.getSelectedItem());	//select year
				mail=tmail.getText();
				mno=tmn.getText();
				cnt=ref.count(ayr, id);
				cnt=cnt+1;
				if(id<10)
					pno=""+ayr+"0"+id+cnt;
				else
					pno=""+ayr+id+cnt;
				ref.delete(dpno);
				ref.insert(pno, ayr, id, tnm, snm, mno, bdate, mail, 1);
			} catch (Exception e6){}
		}
		setVisible(false);
	}
	public void focusGained(FocusEvent e){}
	public void focusLost(FocusEvent e)
	{
		JTextField t=(JTextField)e.getSource();
		if(t==tid)
		{
			try 
			{
				id=Integer.parseInt(tid.getText());
			} catch (Exception e7) 
			{
				tid.requestFocus();
				return;
			}
			try 
			{
				ref.searchTr(id);
				ResultSet rs=ref.rs;
				if(rs.next())
				{
					ttnm.setText(rs.getString(2));
				}
				else
				{
					tid.requestFocus();
					return;
				}
			} 
			catch (Exception e8){}
		}
	}
}


class DelRecord extends JDialog implements ActionListener,FocusListener
{
	JLabel lyr,lid,lnm,lsnm,lbd,lmn,lmail,lpnr;
	JButton ba,bb;
	JComboBox yr,pnr;
	JTextField tid,ttnm,tsnm,tmail;
	NTextField tmn;
	int cnt,id,ayr;
	NJDatePicker bd;
	String tnm,snm,pno,mail,mno,dpno;
	DB ref;
	Date bdate;
	SimpleDateFormat dateFormat2=new SimpleDateFormat("dd-mm-yyyy");
	DelRecord(JFrame frm,String title,boolean state,DB ref)
	{
		super(frm,title,state);
		this.ref=ref;
		lpnr=new JLabel("PNR No");
		lyr=new JLabel("AYear");
		lid=new JLabel("Trade ID");
		lbd = new JLabel("Birth date");
		lnm=new JLabel("Trade Name");
		lmn = new JLabel("Mobile number");
		lsnm=new JLabel("Student Name");
		lmail=new JLabel("Mail ID");
		ba=new JButton("Delete");
		bb=new JButton("Back");
		ba.addActionListener(this);
		bb.addActionListener(this);
		tid=new JTextField(5);
		tid.setEditable(false);
		tid.addFocusListener(this);
		tsnm=new JTextField(40);
		tsnm.setEditable(false);
		ttnm=new JTextField(40);
		ttnm.setEditable(false);
		ttnm=new JTextField(40);
		tmn=new NTextField(15);
		tmn.setEditable(false);
		tmail=new JTextField(40);
		tmail.setEditable(false);
		yr=new JComboBox();
		yr.setEditable(false);
		pnr=new JComboBox();
		ref.displayStudent();
		ResultSet rs=ref.rs;
		try 
		{
			while(rs.next())
			{
				String s=rs.getString(1);
				if(rs.getInt(9)==1)
					pnr.addItem(s);
			}	//adds each student pnr to combo box
		} catch (Exception e) {}
		pnr.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				dpno=""+pnr.getSelectedItem();
				ref.searchPRN(dpno);
				ResultSet rs=ref.rs;
					try 
					{
						if(rs.next())
						{
							pno=rs.getString(1);
							ayr=rs.getInt(2);
							id=rs.getInt(3);
							tnm=rs.getString(4);
							snm=rs.getString(5);
							mno=rs.getString(6);
							bdate=rs.getDate(7);
							String s=dateFormat2.format(bdate);
							bd.dateField.setText(s);
							mail=rs.getString(8);
							yr.setSelectedItem(""+ayr);
							tsnm.setText(snm);
							tid.setText(""+id);
							ttnm.setText(tnm);
							tmn.setText(mno);
							tmail.setText(mail);
						}	
					}
					 catch (Exception e6) {}
			}
		});
		for(cnt=2000;cnt<=2033;cnt++)
			yr.addItem(""+cnt);
		bd=new NJDatePicker();
		bd.dp.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				bdate=(Date)bd.getSqlDate();
			}
		});
		setLayout(null);
		lpnr.setBounds(50,50,100,30);
		lsnm.setBounds(50, 90, 100, 30);
		lyr.setBounds(50, 130, 100, 30);
		lid.setBounds(50, 170, 100, 30);
		lnm.setBounds(50, 210, 100, 30);
		lbd.setBounds(50, 250, 100, 30);
		lmn.setBounds(50, 290, 100, 30);
		lmail.setBounds(50, 330, 100, 30);

		pnr.setBounds(170, 50, 100, 30);
		tsnm.setBounds(170, 90, 100, 30);
		yr.setBounds(170, 130, 100, 30);
		tid.setBounds(170, 170, 100, 30);
		ttnm.setBounds(170, 210, 100, 30);
		bd.setBounds(170, 250, 200, 30);
		tmn.setBounds(170, 290, 100, 30);
		tmail.setBounds(170, 330, 100, 30);

		ba.setBounds(50, 380, 100, 30);
		bb.setBounds(200, 380, 100, 30);
		ttnm.setEditable(false);
		add(lpnr);
		add(pnr);
		add(lsnm);
		add(tsnm);
		add(lyr);
		add(yr);
		add(lid);
		add(tid);
		add(lnm);
		add(ttnm);
		add(lbd);
		add(bd);
		add(lmn);
		add(tmn);
		add(lmail);
		add(tmail);
 		add(ba);
		add(bb);
 		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(400, 480);
		setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
		JButton b=(JButton)e.getSource();
		if(b==ba)
		{
			try 
			{

				ref.delete(dpno);

			} catch (Exception e6){}
		}
		setVisible(false);
	}
	public void focusGained(FocusEvent e){}
	public void focusLost(FocusEvent e)
	{
		JTextField t=(JTextField)e.getSource();
		if(t==tid)
		{
			try 
			{
				id=Integer.parseInt(tid.getText());
			} catch (Exception e7) 
			{
				tid.requestFocus();
				return;
			}
			try 
			{
				ref.searchTr(id);
				ResultSet rs=ref.rs;
				if(rs.next())
				{
					ttnm.setText(rs.getString(2));
				}
				else
				{
					tid.requestFocus();
					return;
				}
			} 
			catch (Exception e8){}
		}
	}
}

class DisplayRecord extends JDialog implements ActionListener
{
	JTable jt;
	DefaultTableModel dtm;
	JScrollPane jsp;
	JButton bb,bp;
	DB ref;
	ResultSet rs;
	DisplayRecord(JFrame frm, String title, boolean state, DB ref)
	{
		super(frm,title,state);
		this.ref=ref;
		dtm=new DefaultTableModel(new Object[][]{},
			new String []{"PNo","Name","Trade","Birthdate","Mobile","Mail"});
		ref.displayStudent();
		rs=ref.rs;
		try
		{
			int i=0;
			while(rs.next())
			{
				if(rs.getInt(9)==1)
				{
					dtm.insertRow(i,new String[]{
					rs.getString(1),rs.getString(5),
					rs.getString(4),""+rs.getDate(7),
					rs.getString(6),rs.getString(8)});
					i++;
				}
			}
		}
		catch(Exception e){}
		jt=new JTable(dtm);
		jsp=new JScrollPane(jt);
		bb=new JButton("Back");
		bp=new JButton("Print");
		bp.addActionListener(this);
		bb.addActionListener(this);
		JPanel p=new JPanel();
		p.add(bp);
		p.add(bb);
		add(jsp,BorderLayout.CENTER);
		add(p,BorderLayout.SOUTH);
		setSize(500,500);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
		JButton b=(JButton)e.getSource();
		if(b==bp)
		{
			try
			{
				jt.print();
			}
			catch(Exception e1){}
		}
		else
		{
			setVisible(false);
		}
	}
}

public class Application2 extends JFrame implements ActionListener 
{
	JButton ba,bm,bd,bf;
    	DB a;
    	AddRecord b;
    	ModRecord c;
	DelRecord d;
	DisplayRecord f;
    	Application2() 
	{
        	super("Unique PRN Generation");

	        a = new DB();

	        ba = new JButton("Add");
		bm=new JButton("Modify");
		bd =new JButton("Delete");
		bf=new JButton("Display");

        
		ba.addActionListener(this);
		bm.addActionListener(this);
		bd.addActionListener(this);
		bf.addActionListener(this);
        	ba.setBounds(50, 50, 100, 30);
		bm.setBounds(170, 50, 100, 30);
		bd.setBounds(50, 150, 100, 30);
		bf.setBounds(170,150,100,30);
        	setLayout(null);
	        add(ba);
		add(bm);
		add(bd);
		add(bf);
        	setSize(300, 300);
	        setVisible(true);
        	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	}

    	public void actionPerformed(ActionEvent e) 
	{
		JButton b1=(JButton)e.getSource();
		if(b1==ba)
			b=new AddRecord(null, "Add Record", true, a);
		if(b1==bm)
			c=new ModRecord(null, "Mod Record", true, a);
		if(b1==bd)
			d=new DelRecord(null, "Delete Record", true, a);
		if(b1==bf)
			f=new DisplayRecord(null,"Display Record",true,a);
    	}

    	public static void main(String[] args) 
	{
        	Application2 app = new Application2();
    	}
}
