package com.jbank.accountservice.mapper;

import com.jbank.accountservice.dto.AccountDto;
import com.jbank.accountservice.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountMapper {

    AccountDto toDto(Account account);

    List<AccountDto> toDtoList(List<Account> accounts);
}
