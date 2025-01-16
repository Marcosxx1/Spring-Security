package com.marcos.springsec.controller.account;

import com.marcos.springsec.domain.entity.Accounts;

public interface AccountController {

    Accounts getAccountDetails( Long id);
}
