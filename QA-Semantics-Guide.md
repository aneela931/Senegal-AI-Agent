# How to pick a semantic widget

In Katalon, spy the control and copy **Accessibility** (`content-desc` on Android, accessibility id on iOS). Use that value as the locator. Do not use visible text — it changes with language.

An id looks like `login_screen`, `login_screen_text_field`, or `login_screen_button_0`. The index counts only controls of the **same kind** on that screen, top to bottom, starting at `0`. If there is only one of that kind, there is no index.

Widget kinds you will see: `button`, `text_field`, `check_box`, `toggle`, `menu_item`, `banner`. Sheets and popups are their own screen id — use that id, not the page behind it.

## Examples

**The page you are on**

Login is open. Wait for `login_screen`.

**The only text field**

Login has one PIN field. Set text on `login_screen_text_field`. No index.

**Several buttons**

Login buttons, from the top: back, logo, biometric, login.

- Tap back: `login_screen_button_0`
- Tap logo: `login_screen_button_1`
- Tap biometric: `login_screen_button_2`
- Tap login: `login_screen_button_3`

**A switch**

The biometric switch on Login is the only switch. Tap `login_screen_toggle`. No index.

**A checkbox**

Permissions has one checkbox. Tap `user_permissions_screen_check_box`.

**A sheet in front of the page**

The language sheet is open on top of onboarding. Use the sheet, not the page behind it.

- Wait for `language_bottom_sheet`
- Tap close: `language_bottom_sheet_button_0`
- Tap the first language: `language_bottom_sheet_button_1`
- Tap the second language: `language_bottom_sheet_button_2`

**Another screen, same rule**

Onboarding has two buttons. Language is above continue.

- Tap language: `onboarding_screen_button_0`
- Type the phone number: `onboarding_screen_text_field`
- Tap continue: `onboarding_screen_button_1`

**Splash**

Wait for `splash_screen`.

**OTP**

- Wait for `otp_screen`
- Back: `otp_screen_button_0`, logo: `otp_screen_button_1`, retry: `otp_screen_button_2`
- OTP field: `otp_screen_text_field`

**Permissions (after OTP, new device)**

- Wait for `user_permissions_screen`
- Back: `user_permissions_screen_button_0`, logo: `user_permissions_screen_button_1`, confirm: `user_permissions_screen_button_2`
- Terms checkbox: `user_permissions_screen_check_box`

**Biometric enable (after first login, if prompted)**

- Wait for `biometric_enable_screen`
- Enable: `biometric_enable_screen_button_2` (back `_0`, logo `_1`)

**Biometric sheet (turning biometrics on from login or side menu)**

- Wait for `biometric_enable_bottom_sheet`
- Close: `biometric_enable_bottom_sheet_button_0`, OK: `biometric_enable_bottom_sheet_button_1`

**Alerts and loader**

- Error, force update, logout confirm: wait for `alert_popup` — close `_button_0`, OK `_button_1`, cancel `_button_2` when shown
- Blocking spinner: `network_loader`

**After PIN — home shell**

You land on `home_page`. Bottom bar tabs, left to right:

- Dashboard tab: `home_page_button_0`
- Performance tab: `home_page_button_1`
- Notifications tab: `home_page_button_2`
- Profile tab: `home_page_button_3`

**Dashboard (first tab)**

Wait for `dashboard_screen` (inside `home_page`).

Header, left to right:

- Menu (opens side menu): `dashboard_screen_button_0`
- Search: `dashboard_screen_button_1`
- Activity history: `dashboard_screen_button_2`

Balance card, top to bottom:

- Refresh balance: `dashboard_screen_button_3`
- Show / hide balance: `dashboard_screen_button_4`
- QR: `dashboard_screen_button_5`

**Dashboard menu tiles (each use case icon)**

Each tile has its own index. They are numbered across all pages of the grid, not per row.

- First tile: `dashboard_screen_menu_item_0`
- Second tile: `dashboard_screen_menu_item_1`
- Third tile: `dashboard_screen_menu_item_2`
- Continue for every visible tile on the dashboard

**Promotional banners**

- First banner: `dashboard_screen_banner_0`
- Second banner: `dashboard_screen_banner_1`

Nested flows opened from a menu tile are not covered yet — only tap the tile on the dashboard.

**Side menu (drawer open)**

Wait for `side_menu`.

- Back (closes drawer): `side_menu_button_0`
- Language: `side_menu_button_1`

Then account actions (top to bottom). If debug logs appear in the header, they use `side_menu_button_2` and the list below shifts by one:

| Action | Normal build | Debug logs visible |
|--------|----------------|-------------------|
| My profile | `side_menu_button_2` | `side_menu_button_3` |
| Rate app | `side_menu_button_3` | `side_menu_button_4` |
| Share app | `side_menu_button_4` | `side_menu_button_5` |
| Logout | `side_menu_button_5` | `side_menu_button_6` |

Logout opens `alert_popup` — confirm with `alert_popup_button_1`.

Biometric switch in the drawer: `side_menu_toggle`.

**Any new screen**

Spy it, read the id, and count from the top if there is more than one of that kind. If Spy shows no id in this form, report it — do not invent a name.
