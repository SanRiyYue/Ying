public class Student {
    int gradeId;
    int classId;
    String name;
    int score;
    public Student(int gradeId, int classId, String name, int score) {
        this.gradeId = gradeId;
        this.classId = classId;
        this.name = name;
        this.score = score;
    }

    public int getGradeId() {
        return gradeId;
    }


    public int getClassId() {
        return classId;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "gradeId=" + gradeId +
                ", classId=" + classId +
                ", name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}
