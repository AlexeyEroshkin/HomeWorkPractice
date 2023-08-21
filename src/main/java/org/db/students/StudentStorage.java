package org.db.students;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StudentStorage {
    private Map<Long,Student> studentStorageMap = new HashMap<>();
    private StudentSurnameStorage studentSurnameStorage = new StudentSurnameStorage();


    private Long currentId = 0L;
    public Long createStudent(Student student){

            Long nextId = getNextId();
            studentStorageMap.put(nextId, student);
            studentSurnameStorage.studentCreate(nextId, student.getSurname());
            return nextId;
    }
    public boolean updateStudent(Long id, Student student){



        if (!studentStorageMap.containsKey(id)){
            return false;
        } else {
            String newSurname = student.getSurname();
            String oldSurname = studentStorageMap.get(id).getSurname();
            studentSurnameStorage.studentUpdated(id, oldSurname, newSurname);
            studentStorageMap.put(id, student);
            return true;

        }
    }
    public boolean deleteStudent(Long id) {
            Student removes = studentStorageMap.remove(id);
            if (removes != null) {
                String surname = removes.getSurname();
                studentSurnameStorage.studentDelete(id, surname);
            }
            return removes != null;

    }
    public void search(String surname) {
        if (surname == null){
            printAll();

        }
        else if (surname.equals(surname)){
            System.out.println(studentSurnameStorage.getStudentsBySurname(surname).toString());
        }
        else if (surname.equals(surname + "," + surname)) {
            Set<Long> students = studentSurnameStorage.getStudentsBySurnamesLessOrEqualThan(surname);
            for (Long studentId : students) {
                Student student = studentStorageMap.get(studentId);
                System.out.println(studentId);
            }
        }

    }

    public Long getNextId(){
        return currentId++;
    }
   public void printAll(){
        System.out.println(studentStorageMap);

    }
    public void printMap(Map<String, Long> data){
        data.entrySet().forEach(e-> {
            System.out.println(e.getKey() + " " + e.getValue());
        });
    }
    public Map<String,Long> getCountByCourse(){
        return studentStorageMap.values().stream()
              .collect(Collectors.toMap(
                      student -> student.getCourse(),
                      student -> 1L,
                      (count1,count2) -> count1+count2
              ));
    }
    public Map<String,Long> getCountByCity(){
        return studentStorageMap.values().stream()
                .collect(Collectors.toMap(
                        student -> student.getCity(),
                        student -> 1L,
                        (count1,count2) -> count1+count2
                ));
    }
}


