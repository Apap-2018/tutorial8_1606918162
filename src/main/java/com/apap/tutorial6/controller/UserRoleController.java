package com.apap.tutorial6.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apap.tutorial6.model.UserRoleModel;
import com.apap.tutorial6.service.UserRoleService;

@Controller
@RequestMapping("/user")
public class UserRoleController {
	@Autowired
	private UserRoleService userService;
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	private String addUserSubmit(@ModelAttribute UserRoleModel user) {
		userService.addUser(user);
		return "home";
	}
	
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	private String updatePasswordSubmit(String username, String oldPassword, String newPassword, String newPasswordConfirm, Model model) {
		UserRoleModel user = userService.findUserByUsername(username);

		if (userService.isMatch(oldPassword, user.getPassword())) {
			if (newPassword.equals(newPasswordConfirm)) {
				user.setPassword(newPassword);
				userService.addUser(user);
				model.addAttribute("suksesUpdate", "Password berhasil diubah."); 
			}
			else {
				model.addAttribute("gagalUpdate", "Konfirmasi Password Baru tidak sama dengan Password Baru");
			}
		}
		else {
			model.addAttribute("gagalUpdate", "Password lama salah.");
		}
		return "home";
	}

}
