package com.javaproject.employeeservice.service;

import com.javaproject.employeeservice.dto.EmployeeDto;
import com.javaproject.employeeservice.entity.Employee;
import com.javaproject.employeeservice.exception.ResourceNotFoundException;
import com.javaproject.employeeservice.respository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;
    private ModelMapper mapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper mapper) {
        this.employeeRepository = employeeRepository;
        this.mapper = mapper;
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeRepository.save(mapper.map(employeeDto, Employee.class));
        return mapper.map(employee, EmployeeDto.class);
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto employeeDto, Long employeeId) {
        EmployeeDto employeeExist = getEmployeeById(employeeId);

        employeeExist.setName(employeeDto.getName());
        employeeExist.setEmail(employeeDto.getEmail());
        employeeExist.setAge(employeeDto.getAge());
        employeeExist.setGender(employeeDto.getGender());
        employeeExist.setBloodGroup(employeeDto.getBloodGroup());
        employeeExist.setMobile(employeeDto.getMobile());

        return mapper.map(employeeRepository.save(mapper.map(employeeExist, Employee.class)), EmployeeDto.class);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "Id", employeeId));

        return mapper.map(employee, EmployeeDto.class);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {

        return employeeRepository.findAll()
                .stream()
                .map((employee -> mapper.map(employee, EmployeeDto.class)))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteEmployeeById(Long employeeId) {
        EmployeeDto employeeDto = getEmployeeById(employeeId);
        employeeRepository.deleteById(employeeId);
        return true;
    }
}
