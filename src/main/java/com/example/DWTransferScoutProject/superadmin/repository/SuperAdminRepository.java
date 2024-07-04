package com.example.DWTransferScoutProject.superadmin.repository;

import com.example.DWTransferScoutProject.superadmin.entity.SuperAdmin;
import com.example.DWTransferScoutProject.common.account.repository.BaseAccountRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Long>, BaseAccountRepository<SuperAdmin> {

}
