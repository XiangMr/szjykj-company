package com.dome.szjykjcompany.error.LyException;




import com.dome.szjykjcompany.error.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LyException extends RuntimeException{
    private ExceptionEnum exceptionEnum;
}
