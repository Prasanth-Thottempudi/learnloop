package com.learnloop.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learnloop.dao.TeacherRepository;
import com.learnloop.entity.Address;
import com.learnloop.entity.Education;
import com.learnloop.entity.Experience;
import com.learnloop.entity.Teacher;
import com.learnloop.exceptions.ResourceNotFoundException;
import com.learnloop.request.AddressRequest;
import com.learnloop.request.EducationRequest;
import com.learnloop.request.ExperienceRequest;
import com.learnloop.request.TeacherRegistrationRequest;
import com.learnloop.response.AddressResponse;
import com.learnloop.response.EducationResponse;
import com.learnloop.response.ExperienceResponse;
import com.learnloop.response.TeacherResponse;
import com.learnloop.service.TeacherService;

import jakarta.transaction.Transactional;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    @Transactional
    public TeacherResponse registerTeacher(TeacherRegistrationRequest request) {
        Teacher teacher = mapToEntity(request);
        teacher = teacherRepository.save(teacher);
        return mapToResponse(teacher);
    }

    @Override
    public TeacherResponse getTeacherById(Integer id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with ID: " + id));
        return mapToResponse(teacher);
    }

    @Override
    public List<TeacherResponse> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        return teachers.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TeacherResponse updateTeacher(Integer id, TeacherRegistrationRequest request) {
        Teacher existing = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with ID: " + id));

        existing.setTeacherName(request.getTeacherName());
        existing.setTeacherDateOfBirth(request.getTeacherDateOfBirth());
        existing.setTeacherMobileNumber(request.getTeacherMobileNumber());

        if (request.getAddress() != null) {
            Address address = existing.getAddress();
            if (address == null) {
                address = new Address();
                existing.setAddress(address);
            }
            address.setStreet(request.getAddress().getStreet());
            address.setCity(request.getAddress().getCity());
            address.setState(request.getAddress().getState());
            address.setCountry(request.getAddress().getCountry());
            address.setPostalCode(request.getAddress().getZipCode());
        }

        if (request.getExperiences() != null && !request.getExperiences().isEmpty()) {
            existing.getExperiences().clear();
            List<Experience> experienceList = request.getExperiences().stream()
                    .map(exp -> mapToExperience(exp, existing))
                    .collect(Collectors.toList());
            existing.getExperiences().addAll(experienceList);
        }

        if (request.getEducations() != null && !request.getEducations().isEmpty()) {
            existing.getEducations().clear();
            List<Education> educationList = request.getEducations().stream()
                    .map(edu -> mapToEducation(edu, existing))
                    .collect(Collectors.toList());
            existing.getEducations().addAll(educationList);
        }

        Teacher updated = teacherRepository.save(existing);
        return mapToResponse(updated);
    }

    @Override
    @Transactional
    public String deleteTeacher(Integer id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with ID: " + id));
        teacherRepository.delete(teacher);
        return "Teacher with ID " + id + " deleted successfully.";
    }


    private Teacher mapToEntity(TeacherRegistrationRequest request) {
        Teacher teacher = new Teacher();
        teacher.setTeacherName(request.getTeacherName());
        teacher.setTeacherDateOfBirth(request.getTeacherDateOfBirth());
        teacher.setTeacherMobileNumber(request.getTeacherMobileNumber());

        Address address = mapToAddress(request.getAddress());
        teacher.setAddress(address);

        List<Experience> experiences = request.getExperiences().stream()
                .map(exp -> mapToExperience(exp, teacher))
                .collect(Collectors.toList());
        teacher.setExperiences(experiences);

        List<Education> educations = request.getEducations().stream()
                .map(edu -> mapToEducation(edu, teacher))
                .collect(Collectors.toList());
        teacher.setEducations(educations);

        return teacher;
    }

    private Address mapToAddress(AddressRequest request) {
        Address address = new Address();
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setCountry(request.getCountry());
        address.setPostalCode(request.getZipCode());
        return address;
    }

    private Experience mapToExperience(ExperienceRequest req, Teacher teacher) {
        Experience exp = new Experience();
        exp.setInstitutionName(req.getInstitutionName());
        exp.setInstitutionStartDate(LocalDate.parse(req.getInstitutionStartDate()));
        exp.setInstitutionEndDate(LocalDate.parse(req.getInstitutionEndDate()));
        exp.setDesignation(req.getDesignation());
        exp.setTeacher(teacher);
        return exp;
    }

    private Education mapToEducation(EducationRequest req, Teacher teacher) {
        Education edu = new Education();
        edu.setDegreeName(req.getDegreeName());
        edu.setInstitutionName(req.getInstitutionName());
        edu.setFieldOfStudy(req.getFieldOfStudy());
        edu.setStartYear(req.getStartYear());
        edu.setEndYear(req.getEndYear());
        edu.setGrade(req.getGrade());
        edu.setTeacher(teacher);
        return edu;
    }

    private TeacherResponse mapToResponse(Teacher teacher) {
        TeacherResponse response = new TeacherResponse();
        response.setTeacherId(teacher.getTeacherId());
        response.setTeacherName(teacher.getTeacherName());
        response.setTeacherDateOfBirth(teacher.getTeacherDateOfBirth());
        response.setTeacherMobileNumber(teacher.getTeacherMobileNumber());

        response.setAddress(mapToAddressResponse(teacher.getAddress()));

        response.setExperiences(
            teacher.getExperiences().stream()
                .map(this::mapToExperienceResponse)
                .collect(Collectors.toList())
        );

        response.setEducations(
            teacher.getEducations().stream()
                .map(this::mapToEducationResponse)
                .collect(Collectors.toList())
        );

        return response;
    }

    
    private AddressResponse mapToAddressResponse(Address address) {
        AddressResponse response = new AddressResponse();
        response.setAddId(address.getAddId());
        response.setStreet(address.getStreet());
        response.setCity(address.getCity());
        response.setState(address.getState());
        response.setCountry(address.getCountry());
        response.setPostalCode(address.getPostalCode());
        return response;
    }
    
    private ExperienceResponse mapToExperienceResponse(Experience experience) {
        ExperienceResponse res = new ExperienceResponse();
        res.setExpId(experience.getExpId());
        res.setInstitutionName(experience.getInstitutionName());
        res.setInstitutionStartDate(experience.getInstitutionStartDate());
        res.setInstitutionEndDate(experience.getInstitutionEndDate());
        res.setDesignation(experience.getDesignation());
        return res;
    }

    private EducationResponse mapToEducationResponse(Education education) {
        EducationResponse res = new EducationResponse();
        res.setEducationId(education.getEducationId());
        res.setDegreeName(education.getDegreeName());
        res.setInstitutionName(education.getInstitutionName());
        res.setFieldOfStudy(education.getFieldOfStudy());
        res.setStartYear(education.getStartYear());
        res.setEndYear(education.getEndYear());
        res.setGrade(education.getGrade());
        return res;
    }
}
