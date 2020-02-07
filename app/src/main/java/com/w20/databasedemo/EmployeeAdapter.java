package com.w20.databasedemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class EmployeeAdapter extends ArrayAdapter
{
    Context mContext;
    int layoutRes;
    List<Employee> employees;
    SQLiteDatabase mDatabase;

    public EmployeeAdapter(@NonNull Context mContext, int layoutRes, List<Employee> employees, SQLiteDatabase mDatabase)
    {
        super(mContext, layoutRes, employees);
        this.mContext = mContext;
        this.layoutRes = layoutRes;
        this.employees = employees;
        this.mDatabase = mDatabase;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(layoutRes, null);
        TextView tvName = v.findViewById(R.id.tv_name);
        TextView tvSalary = v.findViewById(R.id.tv_salary);
        TextView tvDepartment = v.findViewById(R.id.tv_dept);
        TextView tvJoiningDate = v.findViewById(R.id.tv_joiningdate);

        final Employee employee = employees.get(position);
        tvName.setText(employee.getName());
        tvSalary.setText(String.valueOf(employee.getSalary()));
        tvDepartment.setText(employee.getDept());
        tvJoiningDate.setText(employee.getJoiningdate());

        v.findViewById(R.id.btn_edit_employee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateEmployee(employee);
            }
        });

        v.findViewById(R.id.btn_delete_employee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEmployee(employee);
            }
        });

        return v;

    }

    private void deleteEmployee(final Employee employee)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Are you Sure ?");
        builder.setPositiveButton("Yes 👍", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String sql = "DELETE from employee WHERE id=?";
                mDatabase.execSQL(sql, new Integer[]{employee.getId()});
                loadEmployees();
            }
        });

        builder.setNegativeButton("NO 😈", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void updateEmployee(Employee employee)
    {
        String updatesql = "UPDATE employee SET name = ?, department = ?, joiningdate = ?, salary = ? WHERE id = ?";
        mDatabase.execSQL(updatesql);


    }

    private void loadEmployees()
    {
    }
}
